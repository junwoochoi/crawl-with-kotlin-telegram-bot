package com.example.crawler.notify.service

import com.example.crawler.notify.model.Notify
import com.example.crawler.notify.repository.NotifyRepository
import com.example.crawler.user.model.LastStep
import com.example.crawler.user.model.User
import com.example.crawler.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NotifyService(private val notifyRepository: NotifyRepository, private val userService: UserService) {
    fun registerKeyword(user: User, keyword: String): Notify {
        userService.changeLastStep(user.chatId, LastStep.DONE)
        val foundNotify = notifyRepository.findByUserAndKeyword(user = user, keyword = keyword)
        foundNotify?.let { return it }

       return notifyRepository.save(Notify(user=user, keyword = keyword))
    }
}