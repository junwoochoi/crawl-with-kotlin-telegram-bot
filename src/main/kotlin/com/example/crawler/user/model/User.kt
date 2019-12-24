package com.example.crawler.user.model

import javax.persistence.*

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(name="chat_id", nullable = false)
        val chatId: String
)