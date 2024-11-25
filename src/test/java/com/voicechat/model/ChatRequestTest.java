package com.voicechat.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChatRequestTest {

    @Test
    void testChatRequestGetterAndSetter() {
        // Arrange
        String message = "Test message";
        ChatRequest request = new ChatRequest();

        // Act
        request.setMessage(message);

        // Assert
        assertEquals(message, request.getMessage());
    }

    @Test
    void testChatRequestDefaultConstructor() {
        // Act
        ChatRequest request = new ChatRequest();

        // Assert
        assertNull(request.getMessage());
    }
}
