package com.voicechat.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private OpenAiService openAiServiceClient;

    @PostConstruct
    public void init() {
        openAiServiceClient = new OpenAiService(apiKey);
    }

    public String generateResponse(String message, String model) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("user", message));

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model(model)
                .build();

        return openAiServiceClient.createChatCompletion(completionRequest)
                .getChoices().get(0).getMessage().getContent();
    }
}
