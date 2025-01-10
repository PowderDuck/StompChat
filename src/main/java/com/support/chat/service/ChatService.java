package com.support.chat.service;

import com.support.chat.model.IncomingMessage;

public interface ChatService {
    
    void sendMessage(String chatId, Object message);
    void sendMessage(String chatId, IncomingMessage message);

    String registerChat();

    Iterable<String> getChatIds();
}
