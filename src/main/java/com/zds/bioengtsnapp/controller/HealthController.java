package com.zds.bioengtsnapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HealthController {

    // 不占用根路径 / ，让 static/index.html 能作为主页展示
    @GetMapping({"/health", "/healthz"})
    public Map<String, Object> health() {
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("status", "ok");
        resp.put("time", Instant.now().toString());
        resp.put("app", "bioeng-tsn-app");
        // 只暴露是否存在，不泄露敏感值
        resp.put("env", Map.of(
                "PGHOST", System.getenv("PGHOST") != null,
                "PGPORT", System.getenv("PGPORT") != null,
                "PGDATABASE", System.getenv("PGDATABASE") != null,
                "PGUSER", System.getenv("PGUSER") != null,
                "PGPASSWORD", System.getenv("PGPASSWORD") != null,
                "PORT", System.getenv("PORT") != null
        ));
        return resp;
    }
    @GetMapping({"/patients"})
    public Map<String, Object> patients() {
        return Map.of("patients", "ok");
    }
}


