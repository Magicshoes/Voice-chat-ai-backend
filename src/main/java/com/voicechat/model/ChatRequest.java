package com.voicechat.model;

import lombok.Data;

@Data
public class ChatRequest {
    private String message;
    private String model;
}
