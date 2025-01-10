package com.support.chat.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatLoginDto
{
    @JsonProperty("user_id")
    private String userId;
}
