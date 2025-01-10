package com.support.chat.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.support.chat.constant.ChatConstants;
import com.support.chat.model.incoming.IncomingMessage;
import com.support.chat.model.Message;
import com.support.chat.model.outgoing.OutgoingContent;
import com.support.chat.model.outgoing.OutgoingContentFile;
import com.support.chat.model.outgoing.OutgoingMessage;

@Component
public class MessageMapper {

    private final ObjectMapper mapper = new ObjectMapper();
    
    public OutgoingMessage incomingToOutgoing(
        IncomingMessage incomingMessage, String[] filePaths)
    {
        var content = new OutgoingContent();
        content.setText(
            incomingMessage.getContent().getText());

        var contentFiles = new OutgoingContentFile[
            incomingMessage.getContent().getFiles().length];

        for (var i = 0; i < contentFiles.length; i++)
        {
            var currentOutgoingFile = new OutgoingContentFile();
            var currentIncomingFile = incomingMessage.getContent().getFiles()[i];

            currentOutgoingFile.setUrl(filePaths[i]
                .replace(ChatConstants.LOCAL_STORAGE_PATH, ChatConstants.URL_STORAGE_PATH));
            currentOutgoingFile.setName(currentIncomingFile.getName());
            currentOutgoingFile.setType(currentIncomingFile.getType());

            contentFiles[i] = currentOutgoingFile;
        }
        content.setFiles(contentFiles);

        var outgoingMessage = new OutgoingMessage();
        outgoingMessage.setSenderId(incomingMessage.getSenderId());
        outgoingMessage.setContent(content);
        outgoingMessage.setSendTime(LocalDateTime.now());

        return outgoingMessage;
    }

    public OutgoingMessage messageToOutgoing(Message message)
    {
        try {
            var outgoingMessage = new OutgoingMessage();
            outgoingMessage.setSenderId(message.getSenderId());
    
            outgoingMessage.setContent(
                mapper.readValue(message.getContent(), OutgoingContent.class));
    
            outgoingMessage.setSendTime(message.getSentAt());
    
            return outgoingMessage;
        }
        catch (Exception exception)
        {
            throw new RuntimeException("[-] [messageToOutgoing] Conversion Failure");
        }
    }

    public Message outgoingToMessage(OutgoingMessage outgoingMessage)
    {
        try {
            var message = new Message();
            message.setSenderId(outgoingMessage.getSenderId());
    
            message.setContent(
                mapper.writeValueAsString(outgoingMessage.getContent()));
    
            message.setSentAt(outgoingMessage.getSendTime());
    
            return message;
        }
        catch (Exception exception)
        {
            throw new RuntimeException("[-] [outgoingToMessage] Conversion Failure");
        }
    }
}
