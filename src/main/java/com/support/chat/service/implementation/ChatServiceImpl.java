package com.support.chat.service.implementation;

import java.util.HashSet;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.support.chat.constant.ChatConstants;
import com.support.chat.model.IncomingMessage;
import com.support.chat.service.ChatService;
import com.support.chat.service.MessageService;
import com.support.chat.service.StorageService;
import com.support.chat.utils.MessageMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    
    private final StorageService storageService;
    private final MessageService messageService;

    private final SimpMessageSendingOperations sender;
    private final MessageMapper messageMapper;
    
    private final HashSet<String> chatIds = new HashSet<String>();

    public Iterable<String> getChatIds()
    {
        return chatIds;
    }

    public void sendMessage(String chatId, Object message)
    {
        try {
            log.info(String.format("Sending Message to ChatId %s", chatId));

            convertAndSend(
                ChatConstants.CHAT_ENDPOINT(chatId), message);
        }
        catch (Exception exception)
        {
            System.out.println("[-] ERROR");
        }
    }

    public void sendMessage(String chatId, IncomingMessage message)
    {
        log.info(
                String.format(
                    "Sending Message to ChatId [%s] with SenderId [%s], Text [%s] and FileCount [%s]", 
                    chatId, 
                    message.getSenderId(), 
                    message.getContent().getText(), 
                    message.getContent().getFiles().length));

        var filePaths = storageService.store(
            message.getContent().getFiles());
        var outgoingMessage = messageMapper.incomingToOutgoing(message, filePaths);
        
        messageService.create(outgoingMessage);

        convertAndSend(
            ChatConstants.CHAT_ENDPOINT(chatId), outgoingMessage);
    }

    public String registerChat()
    {
        var randomBytes = new byte[10];
        for (var i = 0; i < randomBytes.length; i++)
        {
            randomBytes[i] = (byte)(48 + Math.round(Math.random() * 9d));
        }
        var chatId = new String(randomBytes);
        chatIds.add(chatId);

        log.info(String.format("Registered Chat with ChatId %s", chatId));

        return chatId;
    }

    private void convertAndSend(String chatId, Object message)
    {
        try 
        {
            sender.convertAndSend(chatId, message);
        }
        catch (Exception exception)
        {
            throw new RuntimeException("[-] Message Send Failure");
        }
    }
}
