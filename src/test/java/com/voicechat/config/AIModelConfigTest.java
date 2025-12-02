package com.voicechat.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AIModelConfig Tests")
class AIModelConfigTest {

    private AIModelConfig aiModelConfig;
    private Map<String, AIModelConfig.ModelProperties> models;

    @BeforeEach
    void setUp() {
        aiModelConfig = new AIModelConfig();
        models = new HashMap<>();
    }

    @Test
    @DisplayName("Should initialize with empty models map")
    void testInitializeWithEmptyModels() {
        assertNull(aiModelConfig.getModels());
    }

    @Test
    @DisplayName("Should set and get models")
    void testSetAndGetModels() {
        AIModelConfig.ModelProperties model = new AIModelConfig.ModelProperties();
        model.setId("gpt-4");
        models.put("openai", model);

        aiModelConfig.setModels(models);

        assertNotNull(aiModelConfig.getModels());
        assertEquals(1, aiModelConfig.getModels().size());
        assertTrue(aiModelConfig.getModels().containsKey("openai"));
    }

    @Test
    @DisplayName("Should handle multiple models in map")
    void testMultipleModelsInMap() {
        AIModelConfig.ModelProperties model1 = new AIModelConfig.ModelProperties();
        model1.setId("gpt-4");
        model1.setProvider("OpenAI");

        AIModelConfig.ModelProperties model2 = new AIModelConfig.ModelProperties();
        model2.setId("claude-3");
        model2.setProvider("Anthropic");

        models.put("openai", model1);
        models.put("anthropic", model2);

        aiModelConfig.setModels(models);

        assertEquals(2, aiModelConfig.getModels().size());
        assertEquals("gpt-4", aiModelConfig.getModels().get("openai").getId());
        assertEquals("claude-3", aiModelConfig.getModels().get("anthropic").getId());
    }

    @Test
    @DisplayName("Should set and get models as null")
    void testSetModelsAsNull() {
        aiModelConfig.setModels(null);
        assertNull(aiModelConfig.getModels());
    }

    @Test
    @DisplayName("ModelProperties should set and get id")
    void testModelPropertiesSetAndGetId() {
        AIModelConfig.ModelProperties modelProperties = new AIModelConfig.ModelProperties();
        modelProperties.setId("gpt-4");

        assertEquals("gpt-4", modelProperties.getId());
    }

    @Test
    @DisplayName("ModelProperties should set and get provider")
    void testModelPropertiesSetAndGetProvider() {
        AIModelConfig.ModelProperties modelProperties = new AIModelConfig.ModelProperties();
        modelProperties.setProvider("OpenAI");

        assertEquals("OpenAI", modelProperties.getProvider());
    }

    @Test
    @DisplayName("ModelProperties should set and get baseUrl")
    void testModelPropertiesSetAndGetBaseUrl() {
        AIModelConfig.ModelProperties modelProperties = new AIModelConfig.ModelProperties();
        String baseUrl = "https://api.openai.com/v1";
        modelProperties.setBaseUrl(baseUrl);

        assertEquals(baseUrl, modelProperties.getBaseUrl());
    }

    @Test
    @DisplayName("ModelProperties should set and get apiKey")
    void testModelPropertiesSetAndGetApiKey() {
        AIModelConfig.ModelProperties modelProperties = new AIModelConfig.ModelProperties();
        String apiKey = "sk-test-key-12345";
        modelProperties.setApiKey(apiKey);

        assertEquals(apiKey, modelProperties.getApiKey());
    }

    @Test
    @DisplayName("ModelProperties should handle all properties together")
    void testModelPropertiesAllPropertiesTogether() {
        AIModelConfig.ModelProperties modelProperties = new AIModelConfig.ModelProperties();
        
        modelProperties.setId("gpt-4");
        modelProperties.setProvider("OpenAI");
        modelProperties.setBaseUrl("https://api.openai.com/v1");
        modelProperties.setApiKey("sk-test-key-12345");

        assertEquals("gpt-4", modelProperties.getId());
        assertEquals("OpenAI", modelProperties.getProvider());
        assertEquals("https://api.openai.com/v1", modelProperties.getBaseUrl());
        assertEquals("sk-test-key-12345", modelProperties.getApiKey());
    }

    @Test
    @DisplayName("ModelProperties should handle null values")
    void testModelPropertiesNullValues() {
        AIModelConfig.ModelProperties modelProperties = new AIModelConfig.ModelProperties();
        
        modelProperties.setId(null);
        modelProperties.setProvider(null);
        modelProperties.setBaseUrl(null);
        modelProperties.setApiKey(null);

        assertNull(modelProperties.getId());
        assertNull(modelProperties.getProvider());
        assertNull(modelProperties.getBaseUrl());
        assertNull(modelProperties.getApiKey());
    }

    @Test
    @DisplayName("ModelProperties should handle empty strings")
    void testModelPropertiesEmptyStrings() {
        AIModelConfig.ModelProperties modelProperties = new AIModelConfig.ModelProperties();
        
        modelProperties.setId("");
        modelProperties.setProvider("");
        modelProperties.setBaseUrl("");
        modelProperties.setApiKey("");

        assertEquals("", modelProperties.getId());
        assertEquals("", modelProperties.getProvider());
        assertEquals("", modelProperties.getBaseUrl());
        assertEquals("", modelProperties.getApiKey());
    }

    @Test
    @DisplayName("Should support updating model properties")
    void testUpdateModelProperties() {
        AIModelConfig.ModelProperties modelProperties = new AIModelConfig.ModelProperties();
        
        modelProperties.setId("gpt-4");
        modelProperties.setProvider("OpenAI");
        
        assertEquals("gpt-4", modelProperties.getId());
        assertEquals("OpenAI", modelProperties.getProvider());
        
        // Update values
        modelProperties.setId("gpt-3.5-turbo");
        modelProperties.setProvider("OpenAI-Updated");
        
        assertEquals("gpt-3.5-turbo", modelProperties.getId());
        assertEquals("OpenAI-Updated", modelProperties.getProvider());
    }

    @Test
    @DisplayName("Models map should retrieve correct model by key")
    void testModelsMapRetrievalByKey() {
        AIModelConfig.ModelProperties model = new AIModelConfig.ModelProperties();
        model.setId("gpt-4");
        model.setProvider("OpenAI");
        model.setBaseUrl("https://api.openai.com/v1");
        model.setApiKey("sk-test-key");

        models.put("primary", model);
        aiModelConfig.setModels(models);

        AIModelConfig.ModelProperties retrieved = aiModelConfig.getModels().get("primary");
        assertNotNull(retrieved);
        assertEquals("gpt-4", retrieved.getId());
        assertEquals("OpenAI", retrieved.getProvider());
    }

    @Test
    @DisplayName("Should handle complex model configurations")
    void testComplexModelConfigurations() {
        // Setup multiple models with different configurations
        AIModelConfig.ModelProperties gpt4 = new AIModelConfig.ModelProperties();
        gpt4.setId("gpt-4");
        gpt4.setProvider("OpenAI");
        gpt4.setBaseUrl("https://api.openai.com/v1");
        gpt4.setApiKey("sk-openai-key");

        AIModelConfig.ModelProperties claude = new AIModelConfig.ModelProperties();
        claude.setId("claude-3");
        claude.setProvider("Anthropic");
        claude.setBaseUrl("https://api.anthropic.com");
        claude.setApiKey("sk-anthropic-key");

        models.put("gpt4-model", gpt4);
        models.put("claude-model", claude);
        aiModelConfig.setModels(models);

        assertEquals(2, aiModelConfig.getModels().size());
        assertEquals("sk-openai-key", aiModelConfig.getModels().get("gpt4-model").getApiKey());
        assertEquals("sk-anthropic-key", aiModelConfig.getModels().get("claude-model").getApiKey());
    }

    @Test
    @DisplayName("Should verify ModelProperties is a static inner class")
    void testModelPropertiesIsStaticClass() {
        // Verify we can instantiate ModelProperties without an outer class instance
        AIModelConfig.ModelProperties model = new AIModelConfig.ModelProperties();
        assertNotNull(model);
    }
}
