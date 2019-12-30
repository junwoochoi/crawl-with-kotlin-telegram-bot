package com.example.crawler.notification.keyword.event

import com.example.crawler.notification.keyword.model.NotificationKeyword
import org.jsoup.nodes.Element

data class NotificationEvent(
        val chatId: Long,
        val notificationKeyword: NotificationKeyword,
        val element : Element
)