package com.example.crawler.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "telegram.bot")
class TelegramProperties {
    lateinit var username: String
    lateinit var key: String
}