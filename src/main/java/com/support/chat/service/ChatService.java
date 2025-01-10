package com.support.chat.service;

import com.support.chat.model.dto.ChatLoginDto;
import com.support.chat.model.dto.ChatRegistrationDto;
import com.support.chat.model.incoming.IncomingMessage;

public interface ChatService {
    
    void sendMessage(String chatId, Object message);
    void sendMessage(String chatId, IncomingMessage message);

    ChatRegistrationDto registerChat(String userId);
    ChatLoginDto loginChat(String userId);

    Iterable<String> getChatIds();
}
