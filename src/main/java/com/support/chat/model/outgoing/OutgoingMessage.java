package com.support.chat.model.outgoing;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OutgoingMessage 
{
    private String senderId;
    private OutgoingContent content;
    private LocalDateTime sendTime;
}
