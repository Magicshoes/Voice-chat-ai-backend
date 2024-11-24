package com.voicechat.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    public String generateResponse(String message, String model) {
        OpenAiService service = new OpenAiService(apiKey);

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("user", message));

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model(model)
                .build();

        return service.createChatCompletion(completionRequest)
                .getChoices().get(0).getMessage().getContent();
    }
}
