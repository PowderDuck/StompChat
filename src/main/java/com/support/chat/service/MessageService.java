package com.support.chat.service;

import org.springframework.data.domain.Pageable;

import com.support.chat.model.OutgoingMessage;

public interface MessageService {

    Long create(OutgoingMessage message);

    OutgoingMessage get(Long id);
    Iterable<OutgoingMessage> getAll(Pageable pageable);

    Long getAllCount();

    void delete(Long id);
}
