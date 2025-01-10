package com.support.chat.model.incoming;

import lombok.Data;

@Data
public class IncomingContent 
{
    private String text;
    private IncomingContentFile[] files;
}
