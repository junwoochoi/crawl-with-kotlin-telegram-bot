package com.example.crawler.notification.keyword.repository

import com.example.crawler.notification.keyword.model.NotificationHistory
import com.example.crawler.notification.keyword.model.NotificationKeyword
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationHistoryRepository : JpaRepository<NotificationHistory, Long> {
    fun existsByNotificationKeywordAndUrl(notificationKeyword: NotificationKeyword, url: String) : Boolean
}