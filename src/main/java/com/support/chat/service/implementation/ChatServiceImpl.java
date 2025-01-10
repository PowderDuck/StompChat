package com.support.chat.service.implementation;

import java.util.HashSet;
import java.util.Set;

import com.support.chat.model.dto.ChatLoginDto;
import com.support.chat.model.dto.ChatRegistrationDto;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.support.chat.constant.ChatConstants;
import com.support.chat.model.incoming.IncomingMessage;
import com.support.chat.service.ChatService;
import com.support.chat.service.MessageService;
import com.support.chat.service.StorageService;
import com.support.chat.mapper.MessageMapper;

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
    
    private final Set<String> chatIds = new HashSet<>();

    public Iterable<String> getChatIds()
    {
        return chatIds;
    }

    public void sendMessage(String chatId, Object message)
    {
        try {
            log.info("Sending Message to ChatId [{}]", chatId);

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
        log.info("Sending Message to ChatId [{}] with SenderId [{}], Text [{}] and FileCount [{}]",
                    chatId, 
                    message.getSenderId(), 
                    message.getContent().getText(), 
                    message.getContent().getFiles().length);

        var filePaths = storageService.store(
            message.getContent().getFiles());
        var outgoingMessage = messageMapper.incomingToOutgoing(message, filePaths);
        
        messageService.create(outgoingMessage);

        convertAndSend(
            ChatConstants.CHAT_ENDPOINT(chatId), outgoingMessage);
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

    public ChatRegistrationDto registerChat(String userId)
    {
        var randomBytes = new byte[10];
        for (var i = 0; i < randomBytes.length; i++)
        {
            randomBytes[i] = (byte)(48 + Math.round(Math.random() * 9d));
        }
        var chatId = new String(randomBytes);
        chatIds.add(chatId);

        log.info("Registered Chat with ChatId [{}]", chatId);

        var registrationDto = new ChatRegistrationDto();
        registrationDto.setUserId(userId);
        registrationDto.setChatId(chatId);

        return registrationDto;
    }

    public ChatLoginDto loginChat(String userId)
    {
        var loginDto = new ChatLoginDto();
        loginDto.setUserId(userId);

        return loginDto;
    }
}
