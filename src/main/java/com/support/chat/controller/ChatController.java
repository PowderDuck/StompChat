package com.support.chat.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.support.chat.model.IncomingMessage;
import com.support.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @SubscribeMapping("/chat/register")
    public Map<String, Object> registerChatter(SimpMessageHeaderAccessor headerAccessor)
    {
        var chatId  = chatService.registerChat();

        var registrationData = new HashMap<String, Object>();
        registrationData.put("user_id", headerAccessor.getSessionId());
        registrationData.put("chat_id", chatId);

        return registrationData;
    }

    @SubscribeMapping("/chat/login")
    public Map<String, Object> loginChatter(SimpMessageHeaderAccessor headerAccessor)
    {
        var loginData = new HashMap<String, Object>();
        loginData.put("user_id", headerAccessor.getSessionId());

        return loginData;
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
