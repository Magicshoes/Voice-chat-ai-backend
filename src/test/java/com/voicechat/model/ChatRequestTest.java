package com.voicechat.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ChatRequest Model Tests")
class ChatRequestTest {

    private ChatRequest chatRequest;

    @BeforeEach
    void setUp() {
        chatRequest = new ChatRequest();
    }

    // ==================== User Message Tests ====================

    @Test
    @DisplayName("Should set and get user message")
    void testSetAndGetUserMessage() {
        String userMessage = "What is the weather today?";
        chatRequest.setUserMessage(userMessage);
        assertEquals(userMessage, chatRequest.getUserMessage());
    }

    @Test
    @DisplayName("Should handle null user message")
    void testNullUserMessage() {
        chatRequest.setUserMessage(null);
        assertNull(chatRequest.getUserMessage());
    }

    @Test
    @DisplayName("Should handle empty user message")
    void testEmptyUserMessage() {
        chatRequest.setUserMessage("");
        assertEquals("", chatRequest.getUserMessage());
    }

    // ==================== System Message Tests ====================

    @Test
    @DisplayName("Should set and get system message")
    void testSetAndGetSystemMessage() {
        String systemMessage = "You are a helpful assistant.";
        chatRequest.setSystemMessage(systemMessage);
        assertEquals(systemMessage, chatRequest.getSystemMessage());
    }

    @Test
    @DisplayName("Should handle null system message")
    void testNullSystemMessage() {
        chatRequest.setSystemMessage(null);
        assertNull(chatRequest.getSystemMessage());
    }

    @Test
    @DisplayName("Should handle empty system message")
    void testEmptySystemMessage() {
        chatRequest.setSystemMessage("");
        assertEquals("", chatRequest.getSystemMessage());
    }

    // ==================== Context Tests ====================

    @Test
    @DisplayName("Should set and get context")
    void testSetAndGetContext() {
        String context = "Previous conversation history and background information";
        chatRequest.setContext(context);
        assertEquals(context, chatRequest.getContext());
    }

    @Test
    @DisplayName("Should handle null context")
    void testNullContext() {
        chatRequest.setContext(null);
        assertNull(chatRequest.getContext());
    }

    @Test
    @DisplayName("Should handle empty context")
    void testEmptyContext() {
        chatRequest.setContext("");
        assertEquals("", chatRequest.getContext());
    }

    // ==================== Model Tests ====================

    @Test
    @DisplayName("Should set and get model")
    void testSetAndGetModel() {
        String model = "gpt-4";
        chatRequest.setModel(model);
        assertEquals(model, chatRequest.getModel());
    }

    @Test
    @DisplayName("Should handle null model")
    void testNullModel() {
        chatRequest.setModel(null);
        assertNull(chatRequest.getModel());
    }

    // ==================== Temperature Tests ====================

    @Test
    @DisplayName("Should have default temperature of 0.7")
    void testDefaultTemperature() {
        assertEquals(0.7, chatRequest.getTemperature());
    }

    @Test
    @DisplayName("Should set and get temperature")
    void testSetAndGetTemperature() {
        chatRequest.setTemperature(0.5);
        assertEquals(0.5, chatRequest.getTemperature());
    }

    @Test
    @DisplayName("Should accept temperature value of 0")
    void testTemperatureZero() {
        chatRequest.setTemperature(0.0);
        assertEquals(0.0, chatRequest.getTemperature());
    }

    @Test
    @DisplayName("Should accept maximum temperature value of 2.0")
    void testMaximumTemperature() {
        chatRequest.setTemperature(2.0);
        assertEquals(2.0, chatRequest.getTemperature());
    }

    // ==================== Max Tokens Tests ====================

    @Test
    @DisplayName("Should have default maxTokens of 2048")
    void testDefaultMaxTokens() {
        assertEquals(2048, chatRequest.getMaxTokens());
    }

    @Test
    @DisplayName("Should set and get maxTokens")
    void testSetAndGetMaxTokens() {
        chatRequest.setMaxTokens(4096);
        assertEquals(4096, chatRequest.getMaxTokens());
    }

    @Test
    @DisplayName("Should accept small maxTokens value")
    void testSmallMaxTokens() {
        chatRequest.setMaxTokens(100);
        assertEquals(100, chatRequest.getMaxTokens());
    }

    @Test
    @DisplayName("Should accept null maxTokens")
    void testNullMaxTokens() {
        chatRequest.setMaxTokens(null);
        assertNull(chatRequest.getMaxTokens());
    }

    // ==================== Top P Tests ====================

    @Test
    @DisplayName("Should have default topP of 1.0")
    void testDefaultTopP() {
        assertEquals(1.0, chatRequest.getTopP());
    }

    @Test
    @DisplayName("Should set and get topP")
    void testSetAndGetTopP() {
        chatRequest.setTopP(0.9);
        assertEquals(0.9, chatRequest.getTopP());
    }

    @Test
    @DisplayName("Should accept topP value of 0")
    void testTopPZero() {
        chatRequest.setTopP(0.0);
        assertEquals(0.0, chatRequest.getTopP());
    }

    @Test
    @DisplayName("Should accept maximum topP value of 1.0")
    void testMaximumTopP() {
        chatRequest.setTopP(1.0);
        assertEquals(1.0, chatRequest.getTopP());
    }

    // ==================== Frequency Penalty Tests ====================

    @Test
    @DisplayName("Should have default frequencyPenalty of 0.0")
    void testDefaultFrequencyPenalty() {
        assertEquals(0.0, chatRequest.getFrequencyPenalty());
    }

    @Test
    @DisplayName("Should set and get frequencyPenalty")
    void testSetAndGetFrequencyPenalty() {
        chatRequest.setFrequencyPenalty(0.5);
        assertEquals(0.5, chatRequest.getFrequencyPenalty());
    }

    @Test
    @DisplayName("Should accept minimum frequencyPenalty value of -2.0")
    void testMinimumFrequencyPenalty() {
        chatRequest.setFrequencyPenalty(-2.0);
        assertEquals(-2.0, chatRequest.getFrequencyPenalty());
    }

    @Test
    @DisplayName("Should accept maximum frequencyPenalty value of 2.0")
    void testMaximumFrequencyPenalty() {
        chatRequest.setFrequencyPenalty(2.0);
        assertEquals(2.0, chatRequest.getFrequencyPenalty());
    }

    // ==================== Presence Penalty Tests ====================

    @Test
    @DisplayName("Should have default presencePenalty of 0.0")
    void testDefaultPresencePenalty() {
        assertEquals(0.0, chatRequest.getPresencePenalty());
    }

    @Test
    @DisplayName("Should set and get presencePenalty")
    void testSetAndGetPresencePenalty() {
        chatRequest.setPresencePenalty(0.5);
        assertEquals(0.5, chatRequest.getPresencePenalty());
    }

    @Test
    @DisplayName("Should accept minimum presencePenalty value of -2.0")
    void testMinimumPresencePenalty() {
        chatRequest.setPresencePenalty(-2.0);
        assertEquals(-2.0, chatRequest.getPresencePenalty());
    }

    @Test
    @DisplayName("Should accept maximum presencePenalty value of 2.0")
    void testMaximumPresencePenalty() {
        chatRequest.setPresencePenalty(2.0);
        assertEquals(2.0, chatRequest.getPresencePenalty());
    }

    // ==================== Default Constructor Tests ====================

    @Test
    @DisplayName("Should initialize default values in constructor")
    void testDefaultConstructor() {
        ChatRequest request = new ChatRequest();
        
        assertNull(request.getUserMessage());
        assertNull(request.getSystemMessage());
        assertNull(request.getContext());
        assertNull(request.getModel());
        assertEquals(0.7, request.getTemperature());
        assertEquals(2048, request.getMaxTokens());
        assertEquals(1.0, request.getTopP());
        assertEquals(0.0, request.getFrequencyPenalty());
        assertEquals(0.0, request.getPresencePenalty());
    }

    // ==================== Integration Tests ====================

    @Test
    @DisplayName("Should handle complete request with all fields")
    void testCompleteRequest() {
        chatRequest.setUserMessage("Tell me a story");
        chatRequest.setSystemMessage("You are a creative storyteller");
        chatRequest.setContext("Previous stories: ...");
        chatRequest.setModel("gpt-4");
        chatRequest.setTemperature(0.8);
        chatRequest.setMaxTokens(4096);
        chatRequest.setTopP(0.95);
        chatRequest.setFrequencyPenalty(1.0);
        chatRequest.setPresencePenalty(0.5);

        assertEquals("Tell me a story", chatRequest.getUserMessage());
        assertEquals("You are a creative storyteller", chatRequest.getSystemMessage());
        assertEquals("Previous stories: ...", chatRequest.getContext());
        assertEquals("gpt-4", chatRequest.getModel());
        assertEquals(0.8, chatRequest.getTemperature());
        assertEquals(4096, chatRequest.getMaxTokens());
        assertEquals(0.95, chatRequest.getTopP());
        assertEquals(1.0, chatRequest.getFrequencyPenalty());
        assertEquals(0.5, chatRequest.getPresencePenalty());
    }

    @Test
    @DisplayName("Should allow overriding default parameter values")
    void testOverridingDefaults() {
        chatRequest.setTemperature(1.5);
        chatRequest.setMaxTokens(1024);
        chatRequest.setTopP(0.85);
        chatRequest.setFrequencyPenalty(-1.5);
        chatRequest.setPresencePenalty(1.2);

        assertEquals(1.5, chatRequest.getTemperature());
        assertEquals(1024, chatRequest.getMaxTokens());
        assertEquals(0.85, chatRequest.getTopP());
        assertEquals(-1.5, chatRequest.getFrequencyPenalty());
        assertEquals(1.2, chatRequest.getPresencePenalty());
    }

    @Test
    @DisplayName("Should handle request with only required fields")
    void testMinimalRequest() {
        chatRequest.setUserMessage("Hello");
        chatRequest.setModel("gpt-3.5-turbo");

        assertEquals("Hello", chatRequest.getUserMessage());
        assertEquals("gpt-3.5-turbo", chatRequest.getModel());
        assertNull(chatRequest.getSystemMessage());
        assertNull(chatRequest.getContext());
        assertEquals(0.7, chatRequest.getTemperature());
        assertEquals(2048, chatRequest.getMaxTokens());
    }
}
