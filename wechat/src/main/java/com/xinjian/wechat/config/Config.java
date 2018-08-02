package com.xinjian.wechat.config;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties
public class Config {

    private Cors cors = new Cors();

    private Jwt jwt = new Jwt();


    @Data
    public static class Cors {
        private List<String> allowedOrigins = new ArrayList<>();

        private List<String> allowedMethods = new ArrayList<>();

        private List<String> allowedHeaders = new ArrayList<>();


    }

    @Data
    public static class Jwt {
        private String header;

        private String secret;

        private Long expiration;

        private String issuer;

        private String authenticationPath;

    }
}
