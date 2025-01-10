package com.support.chat.model;

import lombok.Data;

@Data
public class IncomingContent 
{
    private String text;
    private IncomingContentFile[] files;
}
