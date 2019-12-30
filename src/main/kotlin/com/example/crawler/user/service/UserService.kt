package com.example.crawler.user.service

import com.example.crawler.notification.keyword.repository.NotificationKeywordRepository
import com.example.crawler.user.model.LastStep
import com.example.crawler.user.model.User
import com.example.crawler.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(private val userRepository: UserRepository, private val notificationRepository: NotificationKeywordRepository) {

    fun createUser(chatId: Long, firstName: String = "", lastName: String = ""): User {
        val findUser = userRepository.findByChatId(chatId)
        findUser?.let { return it }

        val user = User(chatId = chatId, lastName = lastName, firstName = firstName)
        return userRepository.save(user)
    }

    fun changeLastStep(chatId: Long, lastStep: LastStep) {
        val findUser = userRepository.findByChatId(chatId) ?: createUser(chatId)

        findUser.changeLastStep(lastStep)

    }

    fun isWaitingInput(chatId: Long): Boolean {
        val findUser = userRepository.findByChatId(chatId)
        findUser?.let { return it.lastStep == LastStep.KEYWORD_WAITING }

        return false
    }

    fun findUser(chatId: Long): User = userRepository.findByChatId(chatId) ?: createUser(chatId)
    fun getKeywordList(chatId: Long): List<String> {
        val findUser = userRepository.findByChatId(chatId)
        findUser?.let { user ->
            return notificationRepository.findByUser(user).map { it.keyword }
        }

        return emptyList()
    }
}