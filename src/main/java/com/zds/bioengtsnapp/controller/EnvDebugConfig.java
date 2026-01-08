package com.zds.bioengtsnapp.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvDebugConfig {

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map<String, String> envMap = new HashMap<>();
        envMap.put("PGHOST", System.getProperty("PGHOST"));
        envMap.put("PGPORT", System.getProperty("PGPORT"));
        envMap.put("PGDATABASE", System.getProperty("PGDATABASE"));
        envMap.put("PGUSER", System.getProperty("PGUSER"));
        envMap.put("PGPASSWORD", System.getProperty("PGPASSWORD"));
        envMap.put("TSURU_APPNAME", System.getProperty("TSURU_APPNAME"));
        envMap.put("TSURU_APPDIR", System.getProperty("TSURU_APPDIR"));
        envMap.put("TSURU_SERVICES", System.getProperty("TSURU_SERVICES"));
        envMap.put("DATABASE_NAME", System.getProperty("DATABASE_NAME"));
        envMap.put("DATABASE_USER", System.getProperty("DATABASE_USER"));
        envMap.put("DATABASE_PASSWORD", System.getProperty("DATABASE_PASSWORD"));
        envMap.put("DATABASE_HOST", System.getProperty("DATABASE_HOST"));
        return envMap;
    }

}
