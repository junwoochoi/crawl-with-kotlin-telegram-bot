package com.example.crawler.notify.model

import com.example.crawler.user.model.User
import javax.persistence.*

@Entity
class Notify(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_user_id")
        val user: User,

        @Column(name = "keyword", nullable = false)
        val keyword: String

)