package com.voicechat.service;

import com.voicechat.config.AIModelConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class OpenAIServiceTest {

    @Autowired
    private OpenAIService openAIService;

    @MockBean
    private AIModelConfig aiModelConfig;

    @BeforeEach
    public void setUp() {
        // Mock the AIModelConfig to return a valid model properties
        AIModelConfig.ModelProperties modelProperties = new AIModelConfig.ModelProperties();
        modelProperties.setProvider("mistral");
        modelProperties.setApiKey("mockApiKey");
        modelProperties.setId("mockModelId");

        when(aiModelConfig.getModels()).thenReturn(Map.of("mistral", modelProperties));
    }

   // @Test
    public void testGenerateResponse() {
        String message = "Hello, how are you?";
        String modelKey = "mistral";
        // String modelKey = "mockModelKey";

        String response = openAIService.generateResponse(message, modelKey);

        assertThat(response).isNotNull();
        assertThat(response).isNotEmpty();
        // Additional assertions can be added based on expected response
    }

    @Test
    public void testGenerateResponseWithInvalidModelKey() {
        String message = "Hello, how are you?";
        String invalidModelKey = "invalidKey";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(message, invalidModelKey);
        });

        assertThat(exception.getMessage()).isEqualTo("Invalid model key: " + invalidModelKey);
    }

    @Test
    public void testGenerateResponseWithNullMessage() {
        String modelKey = "mockModelKey";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(null, modelKey);
        });

        assertThat(exception.getMessage()).isEqualTo("Message cannot be null or empty");
    }

    @Test
    public void testGenerateResponseWithEmptyMessage() {
        String modelKey = "mockModelKey";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse("", modelKey);
        });

        assertThat(exception.getMessage()).isEqualTo("Message cannot be null or empty");
    }
}
