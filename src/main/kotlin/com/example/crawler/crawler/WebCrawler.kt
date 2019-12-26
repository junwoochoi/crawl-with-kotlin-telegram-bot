package com.example.crawler.crawler

import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class WebCrawler {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(initialDelay = 0, fixedDelay = 12000)
    fun crawl() {
        logger.info("크롤 시작!")
        val url = "https://eomisae.co.kr/os"
        val doc = Jsoup.connect(url).get()
        val selector = "#L_ > div._bd.cf.clear > div.card_wrap > div > * > div > div.card_content > h3 > a"
        val elements: Elements = doc.select(selector)
        val eachText = elements.eachText()

        eachText.filter{ it.contains("데이브레이크")}


    }
}