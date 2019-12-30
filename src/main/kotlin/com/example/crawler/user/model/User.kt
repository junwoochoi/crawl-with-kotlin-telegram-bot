package com.example.crawler.user.model

import com.example.crawler.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(name = "chat_id", nullable = false, unique = true)
        val chatId: Long,

        @Column(name = "first_name")
        var firstName: String,

        @Column(name = "last_name")
        var lastName: String,

        @Enumerated(value = EnumType.STRING)
        @Column(name = "last_step", nullable = false)
        var lastStep: LastStep = LastStep.DONE
) : BaseTimeEntity() {
    fun changeLastStep(lastStep: LastStep) {
        this.lastStep = lastStep
    }
}