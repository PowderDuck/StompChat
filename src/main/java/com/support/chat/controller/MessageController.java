package com.support.chat.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.support.chat.model.dto.ResponseDto;
import com.support.chat.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/message")
public class MessageController {

    private final MessageService messageService;
    
    @GetMapping("/get")
    public ResponseDto<?> getMessage(Long id)
    {
        return ResponseDto.ok(
            messageService.get(id));
    }

    @GetMapping("/get-all")
    public ResponseDto<?> getAllMessages(Pageable pageable)
    {
        return ResponseDto.ok(
            messageService.getAll(pageable), messageService.getAllCount(), pageable);
    }

    @GetMapping("/count")
    public ResponseDto<?> getMessageCount()
    {
        return ResponseDto.ok(
            messageService.getAllCount());
    }
}
