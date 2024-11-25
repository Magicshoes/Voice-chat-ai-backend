package com.voicechat.controller;

import com.voicechat.model.ChatRequest;
import com.voicechat.model.ChatResponse;
import com.voicechat.service.OpenAIService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {

    @Mock
    private OpenAIService openAIService;

    @InjectMocks
    private ChatController chatController;

    private static final String DEFAULT_MODEL = "gpt-3.5-turbo";

    @Test
    void chat_ShouldReturnValidResponse() {
        // Arrange
        ChatRequest request = new ChatRequest();
        request.setMessage("Test message");
        request.setModel(DEFAULT_MODEL);
        
        String mockResponse = "Test response";
        when(openAIService.generateResponse(eq("Test message"), eq(DEFAULT_MODEL))).thenReturn(mockResponse);

        // Act
        ChatResponse actualResponse = chatController.chat(request);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(mockResponse, actualResponse.getMessage());
    }

    @Test
    void chat_WithEmptyMessage_ShouldHandleGracefully() {
        // Arrange
        ChatRequest request = new ChatRequest();
        request.setMessage("");
        request.setModel(DEFAULT_MODEL);
        
        String emptyResponse = "";
        when(openAIService.generateResponse(eq(""), eq(DEFAULT_MODEL))).thenReturn(emptyResponse);

        // Act
        ChatResponse response = chatController.chat(request);

        // Assert
        assertNotNull(response);
        assertEquals("", response.getMessage());
    }
}
