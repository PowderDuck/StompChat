package com.support.chat.service.implementation;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.support.chat.model.outgoing.OutgoingMessage;
import com.support.chat.repository.MessageRepository;
import com.support.chat.repository.dao.MessageDao;
import com.support.chat.service.MessageService;
import com.support.chat.mapper.MessageMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final MessageDao messageDao;
    
    public Long create(OutgoingMessage message)
    {
        var savedMessage = messageRepository.save(
            messageMapper.outgoingToMessage(message));

        log.info("Created Message with Id [{}]", savedMessage.getId());

        return savedMessage.getId();
    }

    public OutgoingMessage get(Long id)
    {
        var message = messageRepository.findById(id);
        if (message.isEmpty())
        {
            throw new RuntimeException("[-] Message is Empty");
        }

        return messageMapper.messageToOutgoing(message.get());
    }

    public Iterable<OutgoingMessage> getAll(Pageable pageable)
    {
        return messageRepository.findAll(pageable)
            .stream()
            .map(messageMapper::messageToOutgoing)
            .toList();
    }

    public Long getAllCount()
    {
        return messageDao.getAllCount();
    }

    public void delete(Long id)
    {
        log.info("Deleted Message with Id [{}]", id);

        messageRepository.deleteById(id);
    }
}
