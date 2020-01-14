package com.example.crawler.notification.keyword.model

import com.example.crawler.global.model.BaseTimeEntity
import com.example.crawler.user.model.User
import javax.persistence.*

@Entity
data class NotificationKeyword(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_user_id")
        val user: User,

        @Column(name = "keyword", nullable = false)
        val url: String

) : BaseTimeEntity()