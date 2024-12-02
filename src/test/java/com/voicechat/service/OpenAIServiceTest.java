package com.voicechat.service;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import com.voicechat.config.AIModelConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class OpenAIServiceTest {

    @Mock
    private OpenAiService openAiServiceClient;

    @Mock
    private OpenAiService mistralServiceClient;

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private AIModelConfig aiModelConfig;

    private static final String GPT4_MODEL = "gpt4";
    private static final String MISTRAL_MODEL = "mistral";
    private static final String TEST_QUESTION = "Why is there an electoral college?";

    @BeforeEach
    void setUp() {
        // Setup model clients
        Map<String, OpenAiService> modelClients = new HashMap<>();
        modelClients.put(GPT4_MODEL, openAiServiceClient);
        modelClients.put(MISTRAL_MODEL, mistralServiceClient);
        ReflectionTestUtils.setField(openAIService, "modelClients", modelClients);

        // Setup mock responses
        ChatCompletionResult gpt4Result = createMockResult("GPT-4 test response");
        ChatCompletionResult mistralResult = createMockResult("Mistral test response");

        // Use lenient() for Spring test context
        lenient().when(openAiServiceClient.createChatCompletion(any(ChatCompletionRequest.class)))
            .thenReturn(gpt4Result);
        lenient().when(mistralServiceClient.createChatCompletion(any(ChatCompletionRequest.class)))
            .thenReturn(mistralResult);
    }

    private ChatCompletionResult createMockResult(String content) {
        ChatCompletionResult result = new ChatCompletionResult();
        ChatMessage responseMessage = new ChatMessage();
        responseMessage.setContent(content);
        ChatCompletionChoice choice = new ChatCompletionChoice();
        choice.setMessage(responseMessage);
        result.setChoices(Collections.singletonList(choice));
        return result;
    }

    @Test
    @Tag("integration")
    void testMistralResponse() {
        // Test with Mistral model
        String response = openAIService.generateResponse(TEST_QUESTION, MISTRAL_MODEL);
        assertNotNull(response);
        assertTrue(response.length() > 0);
        assertEquals("Mistral test response", response);
    }

    @Test
    @Tag("integration")
    void testGPT4Response() {
        // Test with GPT-4 model
        String response = openAIService.generateResponse(TEST_QUESTION, GPT4_MODEL);
        assertNotNull(response);
        assertTrue(response.length() > 0);
        assertEquals("GPT-4 test response", response);
    }
}
