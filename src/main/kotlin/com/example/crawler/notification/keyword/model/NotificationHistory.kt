package com.example.crawler.notification.keyword.model

import com.example.crawler.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
data class NotificationHistory(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_notification_id")
        val notificationKeyword : NotificationKeyword,

        @Column(nullable = false, unique = true)
        val url : String
) : BaseTimeEntity()