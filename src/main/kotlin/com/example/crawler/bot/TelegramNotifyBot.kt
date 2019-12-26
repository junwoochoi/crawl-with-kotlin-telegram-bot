package com.example.crawler.bot

import com.example.crawler.config.TelegramProperties
import com.example.crawler.notify.service.NotifyService
import com.example.crawler.user.model.LastStep
import com.example.crawler.user.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

@Component
class TelegramNotifyBot(
        private val telegramProperties: TelegramProperties,
        private val notifyService: NotifyService,
        private val userService: UserService) : TelegramLongPollingBot() {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun getBotUsername(): String = telegramProperties.username

    override fun getBotToken(): String = telegramProperties.key

    override fun onUpdateReceived(update: Update?) {
        if (update == null || !update.hasMessage()) {
            return
        }

        val message = update.message

        logger.info("입력한 chatId : {}", message.chatId)
        logger.info("입력한 username : {}", message.chat.lastName + message.chat.firstName)
        logger.info("입력 받은 메시지 : {}", message.text)


        if (update.message.text == "/start") {
            userService.createUser(
                    chatId = message.chatId,
                    firstName = message.chat.firstName,
                    lastName = message.chat.lastName
            )
            sendButtons(chatId = message.chatId, messageId = message.messageId)
            return
        }

        if (update.message.text == "패션정보 키워드 추가") {
            userService.changeLastStep(chatId = message.chatId, lastStep = LastStep.KEYWORD_WAITING)
            sendMessage(message = "키워드를 입력해주세요",
                    chatId = message.chatId,
                    messageId = message.messageId)
            return
        }

        if (update.message.text == "등록한 키워드 목록 확인") {
            val keywordList = userService.getKeywordList(chatId = message.chatId)
            val messageToSend = """현재까지 등록하신 키워드 입니다. [ ${keywordList.joinToString(",")} ]"""
            sendMessage(message = messageToSend,
                    chatId = message.chatId,
                    messageId = message.messageId)
            return
        }

        if (userService.isWaitingInput(message.chatId)) {
            logger.info("입력 대기 상태에서 입력 받음.")
            notifyService.registerKeyword(user = userService.findUser(message.chatId), keyword = message.text)
            val messageForUser = """키워드 [${message.text}]가 알림 키워드로 등록 되었습니다.", message.chatId, message.messageId)"""
            sendMessage(message = messageForUser, chatId = message.chatId, messageId = message.messageId)
            return
        }


    }

    private fun sendMessage(message: String, chatId: Long, messageId: Int) {
        val sendMessage = SendMessage()
        sendMessage.replyToMessageId = messageId
        sendMessage.chatId = chatId.toString()
        sendMessage.text = message

        execute(sendMessage)
    }

    private fun sendButtons(chatId: Long, messageId: Int) {
        val sendMessage = SendMessage()
        sendMessage.replyToMessageId = messageId
        sendMessage.chatId = chatId.toString()
        sendMessage.text = "원하시는 버튼을 눌러 주세요"

        val replyKeyboardMarkup = ReplyKeyboardMarkup()
        replyKeyboardMarkup.selective = true
        val addKeyWordBtn = KeyboardRow()
        val listKeyWordBtn = KeyboardRow()

        addKeyWordBtn.add("패션정보 키워드 추가")
        listKeyWordBtn.add("등록한 키워드 목록 확인")

        replyKeyboardMarkup.keyboard = listOf(addKeyWordBtn, listKeyWordBtn)
        sendMessage.replyMarkup = replyKeyboardMarkup

        execute(sendMessage)
    }
}