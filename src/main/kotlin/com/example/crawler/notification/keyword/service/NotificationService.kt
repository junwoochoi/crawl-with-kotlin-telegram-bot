package com.example.crawler.notification.keyword.service

import com.example.crawler.notification.keyword.event.NotificationEvent
import com.example.crawler.notification.keyword.model.NotificationKeyword
import com.example.crawler.notification.keyword.repository.NotificationHistoryRepository
import com.example.crawler.notification.keyword.repository.NotificationKeywordRepository
import com.example.crawler.user.model.LastStep
import com.example.crawler.user.model.User
import com.example.crawler.user.service.UserService
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NotificationService(
        private val notificationKeywordRepository: NotificationKeywordRepository,
        private val notificationHistoryRepository: NotificationHistoryRepository,
        private val userService: UserService,
        private val applicationEventPublisher: ApplicationEventPublisher) {
    fun registerKeyword(user: User, keyword: String): NotificationKeyword {
        userService.changeLastStep(user.chatId, LastStep.DONE)
        val foundNotify = notificationKeywordRepository.findByUserAndKeyword(user = user, keyword = keyword)
        foundNotify?.let { return it }

        return notificationKeywordRepository.save(NotificationKeyword(user = user, keyword = keyword))
    }

    fun findAll(): List<NotificationKeyword> {
        return notificationKeywordRepository.findAll()
    }

    fun findKeywordsInElements(elements: Elements) {
        val foundNotificationKeywordList = findAll()

        elements.forEach { element ->
            foundNotificationKeywordList.forEach {
                if (checkShouldSendNotification(element, it)) {
                    val notificationEvent = NotificationEvent(chatId = it.user.chatId,
                            notificationKeyword = it,
                            element = element)
                    applicationEventPublisher.publishEvent(notificationEvent)
                }
            }
        }
    }

    private fun checkShouldSendNotification(element: Element, notificationKeyword: NotificationKeyword): Boolean {
        if (!element.text().contains(notificationKeyword.keyword)) {
            return false
        }
        val isAlreadyNotified = notificationHistoryRepository.existsByNotificationKeywordAndUrl(notificationKeyword = notificationKeyword, url= element.attr("href"))
        return !isAlreadyNotified
    }
}