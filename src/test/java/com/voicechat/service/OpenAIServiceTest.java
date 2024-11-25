package com.voicechat.service;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import com.voicechat.config.AIModelConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenAIServiceTest {

    @Mock
    private OpenAiService openAiServiceClient;

    @Mock
    private AIModelConfig aiModelConfig;

    @InjectMocks
    private OpenAIService openAIService;

    private static final String TEST_MODEL = "gpt4";
    private static final String TEST_API_KEY = "test-api-key";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(openAIService, "openaiApiKey", TEST_API_KEY);
        
        // Setup mock model configuration
        AIModelConfig.ModelProperties modelProps = new AIModelConfig.ModelProperties();
        modelProps.setId("gpt-4");
        modelProps.setProvider("openai");
        
        Map<String, AIModelConfig.ModelProperties> models = new HashMap<>();
        models.put(TEST_MODEL, modelProps);
        
        // Use lenient() to allow unused stubs in error cases
        lenient().when(aiModelConfig.getModels()).thenReturn(models);
        
        // Setup model clients
        Map<String, OpenAiService> modelClients = new HashMap<>();
        modelClients.put(TEST_MODEL, openAiServiceClient);
        ReflectionTestUtils.setField(openAIService, "modelClients", modelClients);
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
        String response = openAIService.generateResponse(input, TEST_MODEL);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void generateResponse_WithNullInput_ShouldHandleGracefully() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(null, TEST_MODEL);
        });
    }

    @Test
    void generateResponse_WithEmptyInput_ShouldHandleGracefully() {
        // Arrange
        String input = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(input, TEST_MODEL);
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
