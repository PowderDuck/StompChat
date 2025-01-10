package com.support.chat.repository.dao.implementation;

import org.springframework.stereotype.Component;

import com.support.chat.repository.dao.MessageDao;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageDaoImpl implements MessageDao {
    
    private final EntityManager entityManager;

    public Long getAllCount()
    {
        return (Long)entityManager.createNativeQuery(
            """
                SELECT count(*) FROM messages;
            """).getSingleResult();
    }
}
