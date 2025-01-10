package com.support.chat.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatRegistrationDto
{
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("chat_id")
    private String chatId;
}
