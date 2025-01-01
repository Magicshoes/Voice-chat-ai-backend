package com.voicechat.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiKeys {
    public static final String MISTRALAI_API_KEY = System.getenv("MISTRAL_AI_API_KEY");
    public static final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");
}
