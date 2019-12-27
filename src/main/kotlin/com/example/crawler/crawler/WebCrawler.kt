package com.example.crawler.crawler

import com.example.crawler.bot.TelegramNotifyBot
import com.example.crawler.notify.dto.NotifyMessage
import com.example.crawler.notify.service.NotifyService
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.ArrayList

@Component
class WebCrawler(private val notifyService: NotifyService, private val telegramNotifyBot: TelegramNotifyBot) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(initialDelay = 0, fixedDelay = 1000 * 30)
    fun crawl() {
        logger.info("크롤 시작!")
        val url = "https://eomisae.co.kr/os"
        val doc = Jsoup.connect(url).get()
        val selector = "#L_ > div._bd.cf.clear > div.card_wrap > div > * > div > div.card_content > h3 > a"
        val elements: Elements = doc.select(selector)

        val foundNotifications = notifyService.findAll()

        elements.forEach { element ->
            foundNotifications.forEach {
                    if(element.text().contains(it.keyword)){
                        val user = it.user
                        val notifyMessage = NotifyMessage(chatId = user.chatId, message = "[${it.keyword}] 의 새로운 글이 등록되었습니다. ${element.attr("href")}")
                        telegramNotifyBot.sendMessage(notifyMessage.message, notifyMessage.chatId)
                    }
                }
        }


    }
}