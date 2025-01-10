package com.support.chat.model.incoming;

import lombok.Data;

@Data
public class IncomingMessage 
{
    private String senderId;
    private IncomingContent content;
}
