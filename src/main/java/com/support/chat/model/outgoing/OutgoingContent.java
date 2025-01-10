package com.support.chat.model.outgoing;

import lombok.Data;

@Data
public class OutgoingContent 
{
    private String text;
    private OutgoingContentFile[] files;
}
