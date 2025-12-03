package com.voicechat.model;

public class ChatRequest {
    private String userMessage;
    private String systemMessage;
    private String context;
    private String model;
    private Double temperature;
    private Integer maxTokens;
    private Double topP;
    private Double frequencyPenalty;
    private Double presencePenalty;

    public ChatRequest() {
        // Default values for LLM parameters
        this.temperature = 0.7;
        this.maxTokens = 2048;
        this.topP = 1.0;
        this.frequencyPenalty = 0.0;
        this.presencePenalty = 0.0;
    }

    // User Message
    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    // System Message
    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    // Context
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    // Model
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Temperature (0.0 - 2.0): Controls randomness/creativity
    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    // Max Tokens: Maximum length of the response
    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    // Top P (0.0 - 1.0): Controls diversity via nucleus sampling
    public Double getTopP() {
        return topP;
    }

    public void setTopP(Double topP) {
        this.topP = topP;
    }

    // Frequency Penalty (-2.0 - 2.0): Reduces repetition of frequent tokens
    public Double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public void setFrequencyPenalty(Double frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    // Presence Penalty (-2.0 - 2.0): Encourages new topics
    public Double getPresencePenalty() {
        return presencePenalty;
    }

    public void setPresencePenalty(Double presencePenalty) {
        this.presencePenalty = presencePenalty;
    }
}
