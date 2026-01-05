package com.zds.bioengtsnapp.controller;

import com.zds.bioengtsnapp.config.ProxyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * 代理控制器 - 将请求转发到 breakeve.cn
 */
@RestController
public class ProxyController {

    private static final Logger logger = LoggerFactory.getLogger(ProxyController.class);

    @Autowired
    @Qualifier("proxyRestTemplate")
    private RestTemplate restTemplate;

    /**
     * 代理 GET 请求 - /courses/**
     */
    @GetMapping("/courses/**")
    public ResponseEntity<String> proxyCoursesGet(HttpServletRequest request) {
        return proxyRequest(request, null, HttpMethod.GET);
    }

    /**
     * 代理 POST 请求 - /courses/**
     */
    @PostMapping("/courses/**")
    public ResponseEntity<String> proxyCoursesPost(HttpServletRequest request, @RequestBody(required = false) String body) {
        return proxyRequest(request, body, HttpMethod.POST);
    }

    /**
     * 代理 GET 请求 - /users/**
     */
    @GetMapping("/users/**")
    public ResponseEntity<String> proxyUsersGet(HttpServletRequest request) {
        return proxyRequest(request, null, HttpMethod.GET);
    }

    /**
     * 代理 POST 请求 - /users/**
     */
    @PostMapping("/users/**")
    public ResponseEntity<String> proxyUsersPost(HttpServletRequest request, @RequestBody(required = false) String body) {
        return proxyRequest(request, body, HttpMethod.POST);
    }

    /**
     * 代理 GET 请求 - /ai/**
     */
    @GetMapping("/ai/**")
    public ResponseEntity<String> proxyAiGet(HttpServletRequest request) {
        return proxyRequest(request, null, HttpMethod.GET);
    }

    /**
     * 代理 POST 请求 - /ai/**
     */
    @PostMapping("/ai/**")
    public ResponseEntity<String> proxyAiPost(HttpServletRequest request, @RequestBody(required = false) String body) {
        return proxyRequest(request, body, HttpMethod.POST);
    }

    /**
     * 通用代理方法
     */
    private ResponseEntity<String> proxyRequest(HttpServletRequest request, String body, HttpMethod method) {
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();
        
        // 构建目标 URL
        String targetUrl = ProxyConfig.TARGET_BASE_URL + requestUri;
        if (queryString != null && !queryString.isEmpty()) {
            targetUrl += "?" + queryString;
        }
        
        logger.info("代理请求: {} {} -> {}", method, requestUri, targetUrl);
        
        try {
            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/json");
            
            // 创建请求实体
            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            
            // 发送请求
            ResponseEntity<String> response = restTemplate.exchange(
                    targetUrl,
                    method,
                    entity,
                    String.class
            );
            
            // 构建响应头
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
            // 添加 CORS 头
            responseHeaders.set("Access-Control-Allow-Origin", "*");
            responseHeaders.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            responseHeaders.set("Access-Control-Allow-Headers", "Content-Type, Authorization");
            
            logger.info("代理响应: {} {} -> 状态码: {}", method, requestUri, response.getStatusCode());
            
            return new ResponseEntity<>(response.getBody(), responseHeaders, response.getStatusCode());
            
        } catch (HttpClientErrorException e) {
            logger.error("代理请求失败: {} {} -> {} {}", method, requestUri, e.getStatusCode(), e.getMessage());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
            responseHeaders.set("Access-Control-Allow-Origin", "*");
            return new ResponseEntity<>(e.getResponseBodyAsString(), responseHeaders, e.getStatusCode());
            
        } catch (Exception e) {
            logger.error("代理请求异常: {} {} -> {}", method, requestUri, e.getMessage());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
            responseHeaders.set("Access-Control-Allow-Origin", "*");
            String errorJson = String.format("{\"error\": \"代理请求失败\", \"message\": \"%s\"}", e.getMessage());
            return new ResponseEntity<>(errorJson, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 处理 OPTIONS 预检请求（CORS）
     */
    @RequestMapping(value = {"/**"}, method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.set("Access-Control-Allow-Headers", "Content-Type, Authorization");
        headers.set("Access-Control-Max-Age", "3600");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}

