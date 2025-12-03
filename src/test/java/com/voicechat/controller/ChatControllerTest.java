package com.voicechat.controller;

import com.voicechat.model.ChatRequest;
import com.voicechat.model.ChatResponse;
import com.voicechat.service.OpenAIService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ChatController Tests")
class ChatControllerTest {

    @Mock
    private OpenAIService openAIService;

    @InjectMocks
    private ChatController chatController;

    private static final String DEFAULT_MODEL = "gpt-3.5-turbo";

    @Test
    @DisplayName("Should return valid response for user message")
    void chat_ShouldReturnValidResponse() {
        // Arrange
        ChatRequest request = new ChatRequest();
        request.setUserMessage("Test message");
        request.setModel(DEFAULT_MODEL);
        
        String mockResponse = "Test response";
        when(openAIService.generateResponse(anyString(), anyString(), any(), any(), any(Double.class), any(Integer.class)))
            .thenReturn(mockResponse);

        // Act
        ChatResponse actualResponse = chatController.chat(request);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(mockResponse, actualResponse.getMessage());
    }

    @Test
    @DisplayName("Should handle empty user message gracefully")
    void chat_WithEmptyMessage_ShouldHandleGracefully() {
        // Arrange
        ChatRequest request = new ChatRequest();
        request.setUserMessage("");
        request.setModel(DEFAULT_MODEL);
        
        String emptyResponse = "";
        when(openAIService.generateResponse(anyString(), anyString(), any(), any(), any(Double.class), any(Integer.class)))
            .thenReturn(emptyResponse);

        // Act
        ChatResponse response = chatController.chat(request);

        // Assert
        assertNotNull(response);
        assertEquals("", response.getMessage());
    }

    @Test
    @DisplayName("Should pass system message to service")
    void chat_WithSystemMessage_ShouldPassToService() {
        // Arrange
        ChatRequest request = new ChatRequest();
        request.setUserMessage("Hello");
        request.setSystemMessage("You are a helpful assistant");
        request.setModel(DEFAULT_MODEL);
        
        String mockResponse = "Hi there!";
        when(openAIService.generateResponse(anyString(), anyString(), any(), any(), any(Double.class), any(Integer.class)))
            .thenReturn(mockResponse);

        // Act
        ChatResponse response = chatController.chat(request);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse, response.getMessage());
    }

    @Test
    @DisplayName("Should pass context to service")
    void chat_WithContext_ShouldPassToService() {
        // Arrange
        ChatRequest request = new ChatRequest();
        request.setUserMessage("What was mentioned before?");
        request.setContext("Previous conversation history");
        request.setModel(DEFAULT_MODEL);
        
        String mockResponse = "You mentioned...";
        when(openAIService.generateResponse(anyString(), anyString(), any(), any(), any(Double.class), any(Integer.class)))
            .thenReturn(mockResponse);

        // Act
        ChatResponse response = chatController.chat(request);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse, response.getMessage());
    }

    @Test
    @DisplayName("Should pass temperature parameter to service")
    void chat_WithTemperature_ShouldPassToService() {
        // Arrange
        ChatRequest request = new ChatRequest();
        request.setUserMessage("Tell me a story");
        request.setTemperature(0.9);
        request.setModel(DEFAULT_MODEL);
        
        String mockResponse = "Once upon a time...";
        when(openAIService.generateResponse(anyString(), anyString(), any(), any(), any(Double.class), any(Integer.class)))
            .thenReturn(mockResponse);

        // Act
        ChatResponse response = chatController.chat(request);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse, response.getMessage());
    }

    @Test
    @DisplayName("Should pass maxTokens parameter to service")
    void chat_WithMaxTokens_ShouldPassToService() {
        // Arrange
        ChatRequest request = new ChatRequest();
        request.setUserMessage("Hello");
        request.setMaxTokens(100);
        request.setModel(DEFAULT_MODEL);
        
        String mockResponse = "Hi";
        when(openAIService.generateResponse(anyString(), anyString(), any(), any(), any(Double.class), any(Integer.class)))
            .thenReturn(mockResponse);

        // Act
        ChatResponse response = chatController.chat(request);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse, response.getMessage());
    }

    @Test
    @DisplayName("Should handle complete request with all parameters")
    void chat_WithCompleteRequest_ShouldPassAllParameters() {
        // Arrange
        ChatRequest request = new ChatRequest();
        request.setUserMessage("Tell me a creative story");
        request.setSystemMessage("You are a storyteller");
        request.setContext("Previous stories in series");
        request.setModel(DEFAULT_MODEL);
        request.setTemperature(0.9);
        request.setMaxTokens(1024);
        
        String mockResponse = "Once upon a time...";
        when(openAIService.generateResponse(anyString(), anyString(), any(), any(), any(Double.class), any(Integer.class)))
            .thenReturn(mockResponse);

        // Act
        ChatResponse response = chatController.chat(request);

        // Assert
        assertNotNull(response);
        assertEquals(mockResponse, response.getMessage());
    }
}
