package com.support.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ChatApplication {

	public static void main(String[] args) {
		System.setProperty("org.apache.tomcat.websocket.DEFAULT_BUFFER_SIZE", "1048576");
		SpringApplication.run(ChatApplication.class, args);
	}

}
