package com.example.crawler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.telegram.telegrambots.ApiContextInitializer

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
class CrawlerApplication

fun main(args: Array<String>) {
	ApiContextInitializer.init()

	runApplication<CrawlerApplication>(*args)
}


