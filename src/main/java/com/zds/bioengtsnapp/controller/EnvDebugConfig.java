package com.zds.bioengtsnapp.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvDebugConfig {

    private final Environment env;

    public EnvDebugConfig(Environment env) {
        this.env = env;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map<String, String> envMap = new HashMap<>();
        envMap.put("PGHOST", env.getProperty("PGHOST"));
        envMap.put("PGPORT", env.getProperty("PGPORT"));
        envMap.put("PGDATABASE", env.getProperty("PGDATABASE"));
        envMap.put("PGUSER", env.getProperty("PGUSER"));
        envMap.put("PGPASSWORD", env.getProperty("PGPASSWORD"));
        envMap.put("TSURU_APPNAME", env.getProperty("TSURU_APPNAME"));
        envMap.put("TSURU_APPDIR", env.getProperty("TSURU_APPDIR"));
        envMap.put("TSURU_SERVICES", env.getProperty("TSURU_SERVICES"));
        envMap.put("DATABASE_NAME", env.getProperty("DATABASE_NAME"));
        envMap.put("DATABASE_USER", env.getProperty("DATABASE_USER"));
        envMap.put("DATABASE_PASSWORD", env.getProperty("DATABASE_PASSWORD"));
        envMap.put("DATABASE_HOST", env.getProperty("DATABASE_HOST"));
        return envMap;
    }

}
