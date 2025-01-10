package com.support.chat.model;

import lombok.Data;

@Data
public class IncomingMessage 
{
    private String senderId;
    private IncomingContent content;
}
