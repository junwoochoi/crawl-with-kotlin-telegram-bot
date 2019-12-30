package com.example.crawler.notification.keyword.listener

import com.example.crawler.bot.TelegramNotifyBot
import com.example.crawler.notification.keyword.event.NotificationEvent
import com.example.crawler.notification.keyword.model.NotificationHistory
import com.example.crawler.notification.keyword.repository.NotificationHistoryRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NotificationListener(private val telegramNotifyBot: TelegramNotifyBot,
                           private val notificationHistoryRepository: NotificationHistoryRepository) {

    @EventListener
    fun handleNotificationEvent(notificationEvent: NotificationEvent) {
        val notificationKeyword = notificationEvent.notificationKeyword
        val url = notificationEvent.element.attr("href")

        telegramNotifyBot.sendMessage(
                """[${notificationKeyword.keyword}] 의 새로운 게시글이 업로드 되었습니다!""",
                notificationEvent.chatId)
        telegramNotifyBot.sendMessage("[새로운 게시글 주소] :  $url"
                , notificationEvent.chatId)

        notificationHistoryRepository.save(NotificationHistory(notificationKeyword = notificationKeyword, url = url))
    }
}