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
        return envMap;
    }

}
