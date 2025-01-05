package com.voicechat.model;

public class ChatResponse {
    private String message;

    public ChatResponse() {
    }

    public ChatResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
