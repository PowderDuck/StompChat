package com.support.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.support.chat.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> { }
