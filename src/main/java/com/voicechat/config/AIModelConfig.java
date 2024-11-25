package com.voicechat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "ai.model")
public class AIModelConfig {
    private Map<String, ModelProperties> models;

    public Map<String, ModelProperties> getModels() {
        return models;
    }

    public void setModels(Map<String, ModelProperties> models) {
        this.models = models;
    }

    public static class ModelProperties {
        private String id;
        private String provider;
        private String baseUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }
}
