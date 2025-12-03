package com.voicechat.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ChatResponse {
    private String message;
    private String model;
    private String role;
    private Integer tokensUsed;
    private Integer promptTokens;
    private Integer completionTokens;
    private LocalDateTime timestamp;
    private Long responseTimeMs;
    private String finishReason;
    private Double confidence;
    private Map<String, Object> metadata;
    private boolean success;
    private String errorMessage;

    public ChatResponse() {
        this.timestamp = LocalDateTime.now();
        this.metadata = new HashMap<>();
        this.success = true;
        this.role = "assistant";
    }

    public ChatResponse(String message) {
        this();
        this.message = message;
    }

    public ChatResponse(String message, String model) {
        this();
        this.message = message;
        this.model = model;
    }

    // Message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Model
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Tokens Used (total)
    public Integer getTokensUsed() {
        return tokensUsed;
    }

    public void setTokensUsed(Integer tokensUsed) {
        this.tokensUsed = tokensUsed;
    }

    // Prompt Tokens
    public Integer getPromptTokens() {
        return promptTokens;
    }

    public void setPromptTokens(Integer promptTokens) {
        this.promptTokens = promptTokens;
    }

    // Completion Tokens
    public Integer getCompletionTokens() {
        return completionTokens;
    }

    public void setCompletionTokens(Integer completionTokens) {
        this.completionTokens = completionTokens;
    }

    // Timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Response Time in milliseconds
    public Long getResponseTimeMs() {
        return responseTimeMs;
    }

    public void setResponseTimeMs(Long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    // Finish Reason (e.g., "stop", "length", "content_filter")
    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    // Confidence Score (0.0 - 1.0)
    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    // Metadata for additional response information
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public void addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
    }

    // Success flag
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Error Message
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
