package com.voicechat.controller;

import com.voicechat.model.ChatRequest;
import com.voicechat.model.ChatResponse;
import com.voicechat.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String response = openAIService.generateResponse(request.getMessage(), request.getModel());
        return new ChatResponse(response);
    }
}
