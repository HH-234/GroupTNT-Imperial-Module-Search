package com.zds.bioengtsnapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 健康检查接口：用于验证应用是否正常启动
 */
@RestController
public class HealthController {

    @GetMapping({"health", "/healthz"})
    public Map<String, Object> health() {
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("status", "ok");
        resp.put("time", Instant.now().toString());
        resp.put("app", "bioeng-tsn-app");
        resp.put("env", Map.of(
                "PGHOST", System.getenv("PGHOST") != null,
                "PGPORT", System.getenv("PGPORT") != null,
                "PGDATABASE", System.getenv("PGDATABASE") != null,
                "PGUSER", System.getenv("PGUSER") != null,
                "PGPASSWORD", System.getenv("PGPASSWORD") != null
        ));
        return resp;
    }
}

