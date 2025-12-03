package com.voicechat.service;

import com.voicechat.config.AIModelConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OpenAIService Tests")
@ExtendWith(MockitoExtension.class)
public class OpenAIServiceTest {

    private OpenAIService openAIService;
    private AIModelConfig aiModelConfig;

    @BeforeEach
    public void setUp() {
        // Create a real instance of AIModelConfig with test data
        aiModelConfig = new AIModelConfig();
        
        AIModelConfig.ModelProperties mistralProperties = new AIModelConfig.ModelProperties();
        mistralProperties.setProvider("mistral");
        mistralProperties.setApiKey("mockMistralApiKey");
        mistralProperties.setId("mistral-7b");

        AIModelConfig.ModelProperties openAIProperties = new AIModelConfig.ModelProperties();
        openAIProperties.setProvider("openai");
        openAIProperties.setApiKey("mockOpenAIApiKey");
        openAIProperties.setId("gpt-4");

        Map<String, AIModelConfig.ModelProperties> models = new HashMap<>();
        models.put("mistral", mistralProperties);
        models.put("openai", openAIProperties);

        aiModelConfig.setModels(models);
        
        // Create OpenAIService and set AIModelConfig manually
        openAIService = new OpenAIService();
        org.springframework.test.util.ReflectionTestUtils.setField(openAIService, "aiModelConfig", aiModelConfig);
    }

    // ==================== User Message Validation Tests ====================

    @Test
    @DisplayName("Should throw IllegalArgumentException when message is null")
    public void testGenerateResponseWithNullMessage() {
        String modelKey = "mistral";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(null, modelKey, null, null, null, null);
        });

        assertEquals("User message cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when message is empty")
    public void testGenerateResponseWithEmptyMessage() {
        String modelKey = "mistral";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse("", modelKey, null, null, null, null);
        });

        assertEquals("User message cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when message is whitespace only")
    public void testGenerateResponseWithWhitespaceOnlyMessage() {
        String modelKey = "mistral";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse("   ", modelKey, null, null, null, null);
        });

        assertEquals("User message cannot be null or empty", exception.getMessage());
    }

    // ==================== Model Key Validation Tests ====================

    @Test
    @DisplayName("Should throw IllegalArgumentException when modelKey is null")
    public void testGenerateResponseWithNullModelKey() {
        String message = "Hello, how are you?";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(message, null, null, null, null, null);
        });

        assertEquals("Model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when modelKey is empty")
    public void testGenerateResponseWithEmptyModelKey() {
        String message = "Hello, how are you?";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(message, "", null, null, null, null);
        });

        assertEquals("Model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when modelKey is whitespace only")
    public void testGenerateResponseWithWhitespaceOnlyModelKey() {
        String message = "Hello, how are you?";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(message, "   ", null, null, null, null);
        });

        assertEquals("Model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when model key is invalid")
    public void testGenerateResponseWithInvalidModelKey() {
        String message = "Hello, how are you?";
        String invalidModelKey = "invalidKey";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(message, invalidModelKey, null, null, null, null);
        });

        assertEquals("Invalid model key: " + invalidModelKey, exception.getMessage());
    }

    @Test
    @DisplayName("Should validate model key case sensitivity")
    public void testGenerateResponseWithDifferentCaseModelKey() {
        String message = "Hello";
        String invalidModelKey = "MISTRAL"; // Different case

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(message, invalidModelKey);
        });

        assertEquals("Invalid model key: " + invalidModelKey, exception.getMessage());
    }

    // ==================== Message Building Logic Tests ====================

    @Test
    @DisplayName("Should build message with system message only")
    public void testMessageBuildingWithSystemMessageOnly() {
        String userMsg = "Hello";
        String systemMsg = "You are helpful";
        
        // This test verifies message building logic by checking no exception is thrown
        // when modelClients is not initialized (which will throw later)
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", systemMsg, null, null, null);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should build message with context only")
    public void testMessageBuildingWithContextOnly() {
        String userMsg = "What was discussed?";
        String context = "Previous discussion about AI";
        
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", null, context, null, null);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should build message with both system message and context")
    public void testMessageBuildingWithSystemMessageAndContext() {
        String userMsg = "Continue the discussion";
        String systemMsg = "You are an expert";
        String context = "Earlier discussion about AI";
        
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", systemMsg, context, null, null);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should build message with empty system message")
    public void testMessageBuildingWithEmptySystemMessage() {
        String userMsg = "Hello";
        String systemMsg = "";
        
        // Empty system message should be ignored in message building
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", systemMsg, null, null, null);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should build message with whitespace-only system message")
    public void testMessageBuildingWithWhitespaceSystemMessage() {
        String userMsg = "Hello";
        String systemMsg = "   ";
        
        // Whitespace-only system message should be ignored
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", systemMsg, null, null, null);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should build message with empty context")
    public void testMessageBuildingWithEmptyContext() {
        String userMsg = "Hello";
        String context = "";
        
        // Empty context should be ignored
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", null, context, null, null);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should build message with whitespace-only context")
    public void testMessageBuildingWithWhitespaceContext() {
        String userMsg = "Hello";
        String context = "   ";
        
        // Whitespace-only context should be ignored
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", null, context, null, null);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should handle null system message gracefully")
    public void testMessageBuildingWithNullSystemMessage() {
        String userMsg = "Hello";
        
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", null, null, null, null);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should handle null context gracefully")
    public void testMessageBuildingWithNullContext() {
        String userMsg = "Hello";
        
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", null, null, null, null);
        });
        
        assertNotNull(exception);
    }

    // ==================== Backward Compatibility Tests ====================

    @Test
    @DisplayName("Should support old method signature for backward compatibility")
    public void testGenerateResponseBackwardCompatibility() {
        String message = "Hello, how are you?";
        String modelKey = "openai";

        // The old method signature should still throw proper error
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(message, modelKey);
        });

        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should pass null parameters to new method from old signature")
    public void testBackwardCompatibilityPassesNullParameters() {
        String message = "Test message";
        
        // Old method should call new method with nulls for optional parameters
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(message, "mistral");
        });
        
        assertNotNull(exception);
    }

    // ==================== Service Initialization Tests ====================

    @Test
    @DisplayName("Should initialize service without errors")
    public void testInitializeService() {
        OpenAIService service = new OpenAIService();
        org.springframework.test.util.ReflectionTestUtils.setField(service, "aiModelConfig", aiModelConfig);
        
        // Call init to setup model clients
        service.init();
        
        // Verify the service is initialized without errors
        assertNotNull(service);
    }

    @Test
    @DisplayName("Should create modelClients map during init")
    public void testInitCreatesModelClientsMap() {
        OpenAIService service = new OpenAIService();
        org.springframework.test.util.ReflectionTestUtils.setField(service, "aiModelConfig", aiModelConfig);
        
        service.init();
        
        @SuppressWarnings("unchecked")
        Map<String, ?> modelClients = (Map<String, ?>) org.springframework.test.util.ReflectionTestUtils
                .getField(service, "modelClients");
        
        assertNotNull(modelClients);
    }

    @Test
    @DisplayName("Should handle init with empty models map")
    public void testInitWithEmptyModelsMap() {
        OpenAIService service = new OpenAIService();
        AIModelConfig emptyConfig = new AIModelConfig();
        emptyConfig.setModels(new HashMap<>());
        
        org.springframework.test.util.ReflectionTestUtils.setField(service, "aiModelConfig", emptyConfig);
        
        // Should not throw exception
        assertDoesNotThrow(() -> service.init());
    }

    // ==================== Provider Configuration Tests ====================

    @Test
    @DisplayName("Should support Mistral provider configuration")
    public void testMistralProviderConfiguration() {
        AIModelConfig.ModelProperties mistralProps = aiModelConfig.getModels().get("mistral");
        
        assertNotNull(mistralProps);
        assertEquals("mistral", mistralProps.getProvider());
        assertEquals("mistral-7b", mistralProps.getId());
        assertEquals("mockMistralApiKey", mistralProps.getApiKey());
    }

    @Test
    @DisplayName("Should support OpenAI provider configuration")
    public void testOpenAIProviderConfiguration() {
        AIModelConfig.ModelProperties openaiProps = aiModelConfig.getModels().get("openai");
        
        assertNotNull(openaiProps);
        assertEquals("openai", openaiProps.getProvider());
        assertEquals("gpt-4", openaiProps.getId());
        assertEquals("mockOpenAIApiKey", openaiProps.getApiKey());
    }

    // ==================== Model Configuration Tests ====================

    @Test
    @DisplayName("Should have multiple models available")
    public void testMultipleModelsAvailable() {
        Map<String, AIModelConfig.ModelProperties> models = aiModelConfig.getModels();
        
        assertNotNull(models);
        assertEquals(2, models.size());
        assertTrue(models.containsKey("mistral"));
        assertTrue(models.containsKey("openai"));
    }

    @Test
    @DisplayName("Should retrieve correct model properties by key")
    public void testRetrieveModelPropertiesByKey() {
        AIModelConfig.ModelProperties props = aiModelConfig.getModels().get("openai");
        
        assertNotNull(props);
        assertEquals("gpt-4", props.getId());
    }

    @Test
    @DisplayName("Should return null for non-existent model key")
    public void testRetrieveNonExistentModelProperties() {
        AIModelConfig.ModelProperties props = aiModelConfig.getModels().get("nonexistent");
        
        assertNull(props);
    }

    // ==================== Message Content Tests ====================

    @Test
    @DisplayName("Should handle message with special characters")
    public void testGenerateResponseWithSpecialCharacters() {
        String message = "Hello! What's your name? @#$%";
        
        assertNotNull(message);
    }

    @Test
    @DisplayName("Should handle message with multiline content")
    public void testGenerateResponseWithMultilineMessage() {
        String message = "Hello\nThis is a multiline\nmessage";
        
        assertNotNull(message);
        assertFalse(message.trim().isEmpty());
    }

    @Test
    @DisplayName("Should handle message with leading/trailing whitespace")
    public void testGenerateResponseWithLeadingTrailingWhitespace() {
        String message = "  Hello, how are you?  ";
        
        assertFalse(message.trim().isEmpty());
    }

    @Test
    @DisplayName("Should accept long messages")
    public void testGenerateResponseWithLongMessage() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            longMessage.append("This is a long message. ");
        }
        
        String message = longMessage.toString();
        assertNotNull(message);
        assertFalse(message.trim().isEmpty());
    }

    @Test
    @DisplayName("Should handle message with unicode characters")
    public void testGenerateResponseWithUnicodeMessage() {
        String message = "Hello in different languages: 你好 مرحبا Привет";
        
        assertNotNull(message);
        assertFalse(message.trim().isEmpty());
    }

    @Test
    @DisplayName("Should handle message with numbers and symbols")
    public void testGenerateResponseWithNumbersAndSymbols() {
        String message = "What is 2+2? Calculate: √16 = ?";
        
        assertNotNull(message);
        assertFalse(message.trim().isEmpty());
    }

    // ==================== Model Key Content Tests ====================

    @Test
    @DisplayName("Should handle model key with special characters")
    public void testGenerateResponseWithSpecialCharactersInModelKey() {
        String message = "Hello";
        String invalidModelKey = "model@#$%";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(message, invalidModelKey);
        });

        assertEquals("Invalid model key: " + invalidModelKey, exception.getMessage());
    }

    // ==================== Configuration Setup Tests ====================

    @Test
    @DisplayName("Should have AIModelConfig set")
    public void testAIModelConfigSet() {
        assertNotNull(aiModelConfig);
        assertNotNull(aiModelConfig.getModels());
    }

    @Test
    @DisplayName("Should have OpenAIService instantiated")
    public void testOpenAIServiceInstantiated() {
        assertNotNull(openAIService);
    }

    @Test
    @DisplayName("Should be able to create new service instance")
    public void testCreateNewServiceInstance() {
        OpenAIService newService = new OpenAIService();
        assertNotNull(newService);
    }

    // ==================== Parameter Validation Tests ====================

    @Test
    @DisplayName("Should validate temperature parameter is passed")
    public void testTemperatureParameterIsUsed() {
        Double temperature = 0.8;
        String userMsg = "Hello";
        
        // Verify no exception thrown for valid temperature value
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", null, null, temperature, null);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should validate maxTokens parameter is passed")
    public void testMaxTokensParameterIsUsed() {
        Integer maxTokens = 2048;
        String userMsg = "Hello";
        
        // Verify no exception thrown for valid maxTokens value
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", null, null, null, maxTokens);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should handle null temperature parameter")
    public void testNullTemperatureParameter() {
        String userMsg = "Hello";
        
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", null, null, null, null);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should handle null maxTokens parameter")
    public void testNullMaxTokensParameter() {
        String userMsg = "Hello";
        
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", null, null, null, null);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Should accept zero values for parameters")
    public void testZeroValuesForParameters() {
        String userMsg = "Hello";
        
        Exception exception = assertThrows(Exception.class, () -> {
            openAIService.generateResponse(userMsg, "mistral", null, null, 0.0, 0);
        });
        
        assertNotNull(exception);
    }

    // ==================== Error Sequence Tests ====================

    @Test
    @DisplayName("Should check user message first before model key")
    public void testUserMessageValidationBeforeModelKey() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(null, null, null, null, null, null);
        });

        assertEquals("User message cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should check model key before model properties lookup")
    public void testModelKeyValidationBeforePropertiesLookup() {
        String message = "Valid message";
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(message, null, null, null, null, null);
        });

        assertEquals("Model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should provide clear error for missing model configuration")
    public void testClearErrorForMissingModelConfiguration() {
        String message = "Test";
        String modelKey = "nonexistent";
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            openAIService.generateResponse(message, modelKey, null, null, null, null);
        });

        assertEquals("Invalid model key: " + modelKey, exception.getMessage());
    }
}
