package com.support.chat.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="messages")
@Data
public class Message 
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String senderId;

    @Column(columnDefinition="TEXT")
    private String content;

    private LocalDateTime sentAt;
}
