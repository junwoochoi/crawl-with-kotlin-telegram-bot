package com.example.crawler.bot

import com.example.crawler.config.TelegramProperties
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

@Component
class TelegramNotifyBot(private val telegramProperties: TelegramProperties) : TelegramLongPollingBot() {

    override fun getBotUsername(): String = telegramProperties.username

    override fun getBotToken(): String = telegramProperties.key

    override fun onUpdateReceived(update: Update?) {
        if (update == null || !update.hasMessage()) {
            return
        }

        val message = update.message
        val chatId = message.chatId


        if (update.message.text == "/start") {
            sendButtons(chatId = message.chatId, messageId = message.messageId)
            return
        }

        if (update.message.text == "패션정보 키워드 추가") {
            sendMessage(message = "키워드를 입력해주세요",
                    chatId = message.chatId,
                    messageId = message.messageId)
            return
        }


        if (update.message.text.startsWith("/키워드추가")) {
            val keyword = update.message.text.split("/키워드 추가")[1]

            sendMessage(message = """요청하신 키워드 : "$keyword" 가 등록 되었습니다""",
                    chatId = message.chatId,
                    messageId = message.messageId)
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
        sendMessage.text = "안녕하세요!"

        sendMessage.text = "이찬구"

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