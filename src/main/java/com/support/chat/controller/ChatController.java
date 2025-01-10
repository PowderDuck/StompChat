package com.support.chat.controller;

import com.support.chat.model.dto.ChatLoginDto;
import com.support.chat.model.dto.ChatRegistrationDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.support.chat.model.incoming.IncomingMessage;
import com.support.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @SubscribeMapping("/chat/register")
    public ChatRegistrationDto registerChatter(SimpMessageHeaderAccessor headerAccessor)
    {
        return chatService.registerChat(
                headerAccessor.getSessionId());
    }

    @SubscribeMapping("/chat/login")
    public ChatLoginDto loginChatter(SimpMessageHeaderAccessor headerAccessor)
    {
        return chatService.loginChat(
                headerAccessor.getSessionId());
    }

    // Create the MessageMapping for the Mapping of the UserData with the SessionId of the User, Map<String, UserData>;

    @MessageMapping("/chat/sendMessage/{chatId}")
    public String sendMessageToChat(
        @DestinationVariable("chatId") String chatId, IncomingMessage incomingMessage)
    {
        chatService.sendMessage(chatId, incomingMessage);

        return "SUCCESS";
    }

    @GetMapping("/get-all")
    public Iterable<String> getChatIds()
    {
        return chatService.getChatIds();
    }
}
