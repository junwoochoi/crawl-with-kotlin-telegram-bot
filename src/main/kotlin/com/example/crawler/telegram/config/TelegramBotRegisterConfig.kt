package com.example.crawler.telegram.config

import com.example.crawler.bot.TelegramNotifyBot
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import javax.annotation.PostConstruct

@Configuration
class TelegramBotRegisterConfig(private val telegramNotifyBot : TelegramNotifyBot) {


    @PostConstruct
    fun registerTelegramBot() {

        val botsApi = TelegramBotsApi()
        try {
            botsApi.registerBot(telegramNotifyBot)
        } catch (e: TelegramApiRequestException) {
            e.printStackTrace()
        }
    }
}