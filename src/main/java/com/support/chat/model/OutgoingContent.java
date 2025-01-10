package com.support.chat.model;

import lombok.Data;

@Data
public class OutgoingContent 
{
    private String text;
    private OutgoingContentFile[] files;
}
