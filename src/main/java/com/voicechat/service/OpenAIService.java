package com.voicechat.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import com.voicechat.config.AIModelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import jakarta.annotation.PostConstruct;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Autowired
    private AIModelConfig aiModelConfig;

    private Map<String, OpenAiService> modelClients;

    @PostConstruct
    public void init() {
        modelClients = new HashMap<>();
        
        for (Map.Entry<String, AIModelConfig.ModelProperties> entry : aiModelConfig.getModels().entrySet()) {
            AIModelConfig.ModelProperties props = entry.getValue();
            if ("openai".equals(props.getProvider())) {
                modelClients.put(entry.getKey(), new OpenAiService(openaiApiKey));
            }
            // Add support for other providers as needed
        }
    }

    public String generateResponse(String message, String modelKey) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        if (modelKey == null || modelKey.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }

        AIModelConfig.ModelProperties modelProps = aiModelConfig.getModels().get(modelKey);
        if (modelProps == null) {
            throw new IllegalArgumentException("Invalid model key: " + modelKey);
        }

        OpenAiService client = modelClients.get(modelKey);
        if (client == null) {
            throw new IllegalArgumentException("No client configured for model: " + modelKey);
        }

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("user", message));

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model(modelProps.getId())
                .build();

        return client.createChatCompletion(completionRequest)
                .getChoices().get(0).getMessage().getContent();
    }
}
