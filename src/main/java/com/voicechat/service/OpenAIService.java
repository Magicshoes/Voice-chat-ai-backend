package com.voicechat.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import com.voicechat.config.AIModelConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;

//import jakarta.annotation.PostConstruct;

@Service
public class OpenAIService {

    // @Value("${openai.api.key}")
    // private String openaiApiKey;

    @Autowired
    private AIModelConfig aiModelConfig;

    private Map<String, ChatLanguageModel> modelClients;

    interface Assistant {

        String chat(String message);
    }

    //@PostConstruct
    public void init() {
        modelClients = new HashMap<>();
        
        for (Map.Entry<String, AIModelConfig.ModelProperties> entry : aiModelConfig.getModels().entrySet()) {
            AIModelConfig.ModelProperties props = entry.getValue();
            if ("openai".equals(props.getProvider())) {
                ChatLanguageModel openaiModel = OpenAiChatModel.builder()
                    .apiKey(props.getApiKey())
                    .modelName(props.getId())
                    .build();
                modelClients.put(entry.getKey(), openaiModel);
            } else if ("mistral".equals(props.getProvider())) {
                ChatLanguageModel mistralmodel = MistralAiChatModel.builder()
                .apiKey(props.getApiKey()) // Replace with your actual API key (p.tostring())
                .modelName(props.getId())
                .build();
                modelClients.put(entry.getKey(), mistralmodel ); // Assuming you have a mistralApiKey
            }
            // Add support for other providers as needed
        }
    }

    public String generateResponse(String message, String modelKey) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        if (modelKey == null || modelKey.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }

        AIModelConfig.ModelProperties modelProps = aiModelConfig.getModels().get(modelKey);
        if (modelProps == null) {
            throw new IllegalArgumentException("Invalid model key: " + modelKey);
        }

        ChatLanguageModel clientModel = modelClients.get(modelKey);
        if (clientModel == null) {
            throw new IllegalArgumentException("No client configured for model: " + modelKey);
        }

        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(clientModel)
                .chatMemory(chatMemory)
                .build();

        String answer = assistant.chat(message);

        ChatMessage cAnswer = new AiMessage(answer);
        chatMemory.add(cAnswer);    

        //Incorrect but idea germ of creating a List<ChatMessage> messages that gets stored or returned separately.
        // List<ChatMessage> messages = chatMemory.getMessages();

        return answer;
    }
}
