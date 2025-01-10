package com.support.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.support.chat.constant.ChatConstants;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry)
    {
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry brokerRegistry)
    {
        brokerRegistry
            .setApplicationDestinationPrefixes("/app")
            .enableSimpleBroker("/chat");
    }

    @Override
    public void configureWebSocketTransport(@NonNull WebSocketTransportRegistration registration)
    {
        registration.setMessageSizeLimit(ChatConstants.CHAT_BUFFER_SIZE);
    }
}
