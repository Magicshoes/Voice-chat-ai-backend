package com.voicechat.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChatResponseTest {

    @Test
    void testChatResponseDefaultConstructor() {
        // Act
        ChatResponse response = new ChatResponse(null);

        // Assert
        assertNull(response.getMessage());
    }
    @Test
    void testChatResponseConstructorAndGetter() {
        // Arrange
        String message = "Test message";

        // Act
        ChatResponse chatResponse = new ChatResponse(message);

        // Assert
        assertEquals(message, chatResponse.getMessage());
    }
    @Test 
    void testChatResponseDefaultSetterAndGetter() {
        // Arrange
        String message = "Test message";
        
        // Act
        ChatResponse response = new ChatResponse();
        response.setMessage(message);

        //Assert
        assertEquals(message, response.getMessage());
    }
}
