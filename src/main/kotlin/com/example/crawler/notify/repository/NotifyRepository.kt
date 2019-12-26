package com.example.crawler.notify.repository

import com.example.crawler.notify.model.Notify
import com.example.crawler.user.model.User
import javassist.compiler.ast.Keyword
import org.springframework.data.jpa.repository.JpaRepository

interface NotifyRepository : JpaRepository<Notify, Long> {
    fun findByUserAndKeyword(user: User, keyword: String): Notify?
    fun findByUser(user: User): List<Notify>
}