package com.example.crawler.crawler

import com.example.crawler.notification.keyword.service.NotificationService
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class WebCrawler(private val notificationService: NotificationService) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(initialDelay = 0, fixedDelay = 1000 * 30)
    fun crawl() {
        logger.info("크롤 시작!")
        val url = "https://eomisae.co.kr/os"
        val doc = Jsoup.connect(url).get()
        val selector = "#L_ > div._bd.cf.clear > div.card_wrap > div > * > div > div.card_content > h3 > a"
        val elements: Elements = doc.select(selector)

        notificationService.findKeywordsInElements(elements)

    }

}
