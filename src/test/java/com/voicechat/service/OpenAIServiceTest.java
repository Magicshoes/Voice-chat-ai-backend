package com.voicechat.service;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenAIServiceTest {

    @Mock
    private OpenAiService openAiServiceClient;

    @InjectMocks
    private OpenAIService openAIService;

    private static final String DEFAULT_MODEL = "gpt-3.5-turbo";
    private static final String TEST_API_KEY = "test-api-key";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(openAIService, "apiKey", TEST_API_KEY);
    }

    @Test
    void generateResponse_ShouldHandleValidInput() {
        // Arrange
        String input = "Test message";
        String expectedResponse = "Test response";
        
        // Create a properly initialized mock result
        ChatCompletionResult mockResult = new ChatCompletionResult();
        ChatMessage responseMessage = new ChatMessage();
        responseMessage.setContent(expectedResponse);
        ChatCompletionChoice choice = new ChatCompletionChoice();
        choice.setMessage(responseMessage);
        mockResult.setChoices(Collections.singletonList(choice));

        when(openAiServiceClient.createChatCompletion(any(ChatCompletionRequest.class)))
            .thenReturn(mockResult);

        // Act
        String response = openAIService.generateResponse(input, DEFAULT_MODEL);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void generateResponse_WithEmptyInput_ShouldHandleGracefully() {
        // Arrange
        String input = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(input, DEFAULT_MODEL);
        });
    }

    @Test
    void generateResponse_WithNullInput_ShouldHandleGracefully() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(null, DEFAULT_MODEL);
        });
    }

    @Test
    void generateResponse_WithNullModel_ShouldHandleGracefully() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse("Test message", null);
        });
    }
}
