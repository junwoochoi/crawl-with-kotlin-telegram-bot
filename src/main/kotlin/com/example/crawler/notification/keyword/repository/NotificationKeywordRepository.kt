package com.example.crawler.notification.keyword.repository

import com.example.crawler.notification.keyword.model.NotificationKeyword
import com.example.crawler.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationKeywordRepository : JpaRepository<NotificationKeyword, Long> {
    fun findByUserAndKeyword(user: User, keyword: String): NotificationKeyword?
    fun findByUser(user: User): List<NotificationKeyword>
}