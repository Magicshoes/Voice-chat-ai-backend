package com.voicechat.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ChatResponse Model Tests")
class ChatResponseTest {

    private ChatResponse chatResponse;

    @BeforeEach
    void setUp() {
        chatResponse = new ChatResponse();
    }

    // ==================== Message Tests ====================

    @Test
    @DisplayName("Should set and get message")
    void testSetAndGetMessage() {
        String message = "Test message";
        chatResponse.setMessage(message);
        assertEquals(message, chatResponse.getMessage());
    }

    @Test
    @DisplayName("Should handle null message")
    void testNullMessage() {
        chatResponse.setMessage(null);
        assertNull(chatResponse.getMessage());
    }

    @Test
    @DisplayName("Should handle empty message")
    void testEmptyMessage() {
        chatResponse.setMessage("");
        assertEquals("", chatResponse.getMessage());
    }

    // ==================== Model Tests ====================

    @Test
    @DisplayName("Should set and get model")
    void testSetAndGetModel() {
        String model = "gpt-4";
        chatResponse.setModel(model);
        assertEquals(model, chatResponse.getModel());
    }

    @Test
    @DisplayName("Should handle null model")
    void testNullModel() {
        chatResponse.setModel(null);
        assertNull(chatResponse.getModel());
    }

    @Test
    @DisplayName("Should initialize model as null")
    void testDefaultModel() {
        ChatResponse response = new ChatResponse();
        assertNull(response.getModel());
    }

    // ==================== Role Tests ====================

    @Test
    @DisplayName("Should have default role of 'assistant'")
    void testDefaultRole() {
        ChatResponse response = new ChatResponse();
        assertEquals("assistant", response.getRole());
    }

    @Test
    @DisplayName("Should set and get role")
    void testSetAndGetRole() {
        chatResponse.setRole("user");
        assertEquals("user", chatResponse.getRole());
    }

    @Test
    @DisplayName("Should support different roles")
    void testDifferentRoles() {
        chatResponse.setRole("system");
        assertEquals("system", chatResponse.getRole());
        
        chatResponse.setRole("assistant");
        assertEquals("assistant", chatResponse.getRole());
        
        chatResponse.setRole("function");
        assertEquals("function", chatResponse.getRole());
    }

    // ==================== Token Count Tests ====================

    @Test
    @DisplayName("Should set and get total tokens used")
    void testSetAndGetTokensUsed() {
        chatResponse.setTokensUsed(150);
        assertEquals(150, chatResponse.getTokensUsed());
    }

    @Test
    @DisplayName("Should set and get prompt tokens")
    void testSetAndGetPromptTokens() {
        chatResponse.setPromptTokens(50);
        assertEquals(50, chatResponse.getPromptTokens());
    }

    @Test
    @DisplayName("Should set and get completion tokens")
    void testSetAndGetCompletionTokens() {
        chatResponse.setCompletionTokens(100);
        assertEquals(100, chatResponse.getCompletionTokens());
    }

    @Test
    @DisplayName("Should handle null token counts")
    void testNullTokenCounts() {
        chatResponse.setTokensUsed(null);
        chatResponse.setPromptTokens(null);
        chatResponse.setCompletionTokens(null);
        
        assertNull(chatResponse.getTokensUsed());
        assertNull(chatResponse.getPromptTokens());
        assertNull(chatResponse.getCompletionTokens());
    }

    @Test
    @DisplayName("Should accept zero token counts")
    void testZeroTokenCounts() {
        chatResponse.setTokensUsed(0);
        chatResponse.setPromptTokens(0);
        chatResponse.setCompletionTokens(0);
        
        assertEquals(0, chatResponse.getTokensUsed());
        assertEquals(0, chatResponse.getPromptTokens());
        assertEquals(0, chatResponse.getCompletionTokens());
    }

    // ==================== Timestamp Tests ====================

    @Test
    @DisplayName("Should initialize timestamp automatically")
    void testDefaultTimestamp() {
        ChatResponse response = new ChatResponse();
        assertNotNull(response.getTimestamp());
        assertTrue(response.getTimestamp() instanceof LocalDateTime);
    }

    @Test
    @DisplayName("Should set and get timestamp")
    void testSetAndGetTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        chatResponse.setTimestamp(now);
        assertEquals(now, chatResponse.getTimestamp());
    }

    @Test
    @DisplayName("Should handle null timestamp")
    void testNullTimestamp() {
        chatResponse.setTimestamp(null);
        assertNull(chatResponse.getTimestamp());
    }

    // ==================== Response Time Tests ====================

    @Test
    @DisplayName("Should set and get response time in milliseconds")
    void testSetAndGetResponseTimeMs() {
        chatResponse.setResponseTimeMs(1500L);
        assertEquals(1500L, chatResponse.getResponseTimeMs());
    }

    @Test
    @DisplayName("Should handle null response time")
    void testNullResponseTime() {
        chatResponse.setResponseTimeMs(null);
        assertNull(chatResponse.getResponseTimeMs());
    }

    @Test
    @DisplayName("Should accept zero response time")
    void testZeroResponseTime() {
        chatResponse.setResponseTimeMs(0L);
        assertEquals(0L, chatResponse.getResponseTimeMs());
    }

    // ==================== Finish Reason Tests ====================

    @Test
    @DisplayName("Should set and get finish reason")
    void testSetAndGetFinishReason() {
        chatResponse.setFinishReason("stop");
        assertEquals("stop", chatResponse.getFinishReason());
    }

    @Test
    @DisplayName("Should support different finish reasons")
    void testDifferentFinishReasons() {
        chatResponse.setFinishReason("length");
        assertEquals("length", chatResponse.getFinishReason());
        
        chatResponse.setFinishReason("content_filter");
        assertEquals("content_filter", chatResponse.getFinishReason());
        
        chatResponse.setFinishReason("tool_calls");
        assertEquals("tool_calls", chatResponse.getFinishReason());
    }

    @Test
    @DisplayName("Should handle null finish reason")
    void testNullFinishReason() {
        chatResponse.setFinishReason(null);
        assertNull(chatResponse.getFinishReason());
    }

    // ==================== Confidence Tests ====================

    @Test
    @DisplayName("Should set and get confidence score")
    void testSetAndGetConfidence() {
        chatResponse.setConfidence(0.95);
        assertEquals(0.95, chatResponse.getConfidence());
    }

    @Test
    @DisplayName("Should accept confidence values between 0 and 1")
    void testConfidenceBoundaries() {
        chatResponse.setConfidence(0.0);
        assertEquals(0.0, chatResponse.getConfidence());
        
        chatResponse.setConfidence(1.0);
        assertEquals(1.0, chatResponse.getConfidence());
        
        chatResponse.setConfidence(0.5);
        assertEquals(0.5, chatResponse.getConfidence());
    }

    @Test
    @DisplayName("Should handle null confidence")
    void testNullConfidence() {
        chatResponse.setConfidence(null);
        assertNull(chatResponse.getConfidence());
    }

    // ==================== Metadata Tests ====================

    @Test
    @DisplayName("Should initialize metadata as empty map")
    void testDefaultMetadata() {
        ChatResponse response = new ChatResponse();
        assertNotNull(response.getMetadata());
        assertTrue(response.getMetadata().isEmpty());
    }

    @Test
    @DisplayName("Should set and get metadata")
    void testSetAndGetMetadata() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", 123);
        
        chatResponse.setMetadata(metadata);
        assertEquals(metadata, chatResponse.getMetadata());
    }

    @Test
    @DisplayName("Should add metadata using helper method")
    void testAddMetadata() {
        chatResponse.addMetadata("source", "openai");
        chatResponse.addMetadata("version", "1.0");
        
        assertEquals("openai", chatResponse.getMetadata().get("source"));
        assertEquals("1.0", chatResponse.getMetadata().get("version"));
    }

    @Test
    @DisplayName("Should add multiple metadata entries")
    void testAddMultipleMetadata() {
        chatResponse.addMetadata("model_id", "gpt-4-turbo");
        chatResponse.addMetadata("temperature", 0.7);
        chatResponse.addMetadata("max_tokens", 2048);
        
        assertEquals(3, chatResponse.getMetadata().size());
    }

    @Test
    @DisplayName("Should handle null metadata")
    void testNullMetadata() {
        chatResponse.setMetadata(null);
        assertNull(chatResponse.getMetadata());
    }

    @Test
    @DisplayName("Should create metadata if null when adding")
    void testAddMetadataCreatesMapIfNull() {
        chatResponse.setMetadata(null);
        chatResponse.addMetadata("key", "value");
        
        assertNotNull(chatResponse.getMetadata());
        assertEquals("value", chatResponse.getMetadata().get("key"));
    }

    // ==================== Success Flag Tests ====================

    @Test
    @DisplayName("Should default success to true")
    void testDefaultSuccess() {
        ChatResponse response = new ChatResponse();
        assertTrue(response.isSuccess());
    }

    @Test
    @DisplayName("Should set and get success flag")
    void testSetAndGetSuccess() {
        chatResponse.setSuccess(false);
        assertFalse(chatResponse.isSuccess());
        
        chatResponse.setSuccess(true);
        assertTrue(chatResponse.isSuccess());
    }

    // ==================== Error Message Tests ====================

    @Test
    @DisplayName("Should set and get error message")
    void testSetAndGetErrorMessage() {
        String errorMsg = "Model not found";
        chatResponse.setErrorMessage(errorMsg);
        assertEquals(errorMsg, chatResponse.getErrorMessage());
    }

    @Test
    @DisplayName("Should handle null error message")
    void testNullErrorMessage() {
        chatResponse.setErrorMessage(null);
        assertNull(chatResponse.getErrorMessage());
    }

    @Test
    @DisplayName("Should handle empty error message")
    void testEmptyErrorMessage() {
        chatResponse.setErrorMessage("");
        assertEquals("", chatResponse.getErrorMessage());
    }

    // ==================== Constructor Tests ====================

    @Test
    @DisplayName("Should initialize with empty constructor")
    void testEmptyConstructor() {
        ChatResponse response = new ChatResponse();
        
        assertNull(response.getMessage());
        assertNull(response.getModel());
        assertEquals("assistant", response.getRole());
        assertTrue(response.isSuccess());
        assertNotNull(response.getTimestamp());
        assertNotNull(response.getMetadata());
    }

    @Test
    @DisplayName("Should initialize with message constructor")
    void testMessageConstructor() {
        ChatResponse response = new ChatResponse("Hello");
        
        assertEquals("Hello", response.getMessage());
        assertEquals("assistant", response.getRole());
        assertTrue(response.isSuccess());
        assertNotNull(response.getTimestamp());
    }

    @Test
    @DisplayName("Should initialize with message and model constructor")
    void testMessageAndModelConstructor() {
        ChatResponse response = new ChatResponse("Hello", "gpt-4");
        
        assertEquals("Hello", response.getMessage());
        assertEquals("gpt-4", response.getModel());
        assertEquals("assistant", response.getRole());
        assertTrue(response.isSuccess());
    }

    // ==================== Integration Tests ====================

    @Test
    @DisplayName("Should handle complete response with all fields")
    void testCompleteResponse() {
        chatResponse.setMessage("Detailed response");
        chatResponse.setModel("gpt-4-turbo");
        chatResponse.setRole("assistant");
        chatResponse.setTokensUsed(150);
        chatResponse.setPromptTokens(50);
        chatResponse.setCompletionTokens(100);
        chatResponse.setResponseTimeMs(2000L);
        chatResponse.setFinishReason("stop");
        chatResponse.setConfidence(0.98);
        chatResponse.setSuccess(true);
        chatResponse.addMetadata("source", "openai");
        
        assertEquals("Detailed response", chatResponse.getMessage());
        assertEquals("gpt-4-turbo", chatResponse.getModel());
        assertEquals("assistant", chatResponse.getRole());
        assertEquals(150, chatResponse.getTokensUsed());
        assertEquals(50, chatResponse.getPromptTokens());
        assertEquals(100, chatResponse.getCompletionTokens());
        assertEquals(2000L, chatResponse.getResponseTimeMs());
        assertEquals("stop", chatResponse.getFinishReason());
        assertEquals(0.98, chatResponse.getConfidence());
        assertTrue(chatResponse.isSuccess());
        assertEquals("openai", chatResponse.getMetadata().get("source"));
    }

    @Test
    @DisplayName("Should handle error response")
    void testErrorResponse() {
        chatResponse.setMessage(null);
        chatResponse.setSuccess(false);
        chatResponse.setErrorMessage("API rate limit exceeded");
        
        assertNull(chatResponse.getMessage());
        assertFalse(chatResponse.isSuccess());
        assertEquals("API rate limit exceeded", chatResponse.getErrorMessage());
    }

    @Test
    @DisplayName("Should calculate total tokens from prompt and completion tokens")
    void testTokenCalculation() {
        chatResponse.setPromptTokens(100);
        chatResponse.setCompletionTokens(200);
        chatResponse.setTokensUsed(300);
        
        assertEquals(300, chatResponse.getTokensUsed());
        assertEquals(100, chatResponse.getPromptTokens());
        assertEquals(200, chatResponse.getCompletionTokens());
    }

    @Test
    @DisplayName("Should support multiple constructors and field modifications")
    void testMultipleConstructorsAndModifications() {
        ChatResponse response1 = new ChatResponse();
        response1.setMessage("Message 1");
        response1.setModel("model-1");
        
        ChatResponse response2 = new ChatResponse("Message 2");
        response2.setModel("model-2");
        
        ChatResponse response3 = new ChatResponse("Message 3", "model-3");
        
        assertEquals("Message 1", response1.getMessage());
        assertEquals("model-1", response1.getModel());
        assertEquals("Message 2", response2.getMessage());
        assertEquals("model-2", response2.getModel());
        assertEquals("Message 3", response3.getMessage());
        assertEquals("model-3", response3.getModel());
    }
}
