package com.example.crawler.user.repository

import com.example.crawler.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByChatId(chatId: Long) : User?
}