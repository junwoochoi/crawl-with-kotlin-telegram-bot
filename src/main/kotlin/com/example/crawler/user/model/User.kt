package com.example.crawler.user.model

import com.example.crawler.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
class User(chatId: Long, firstName: String, lastName: String, lastStep: LastStep = LastStep.DONE) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "chat_id", nullable = false, unique = true)
    val chatId: Long = chatId

    @Column(name = "first_name")
    var firstName: String = firstName

    @Column(name = "last_name")
    var lastName: String = lastName

    @Enumerated(value = EnumType.STRING)
    @Column(name = "last_step", nullable = false)
    var lastStep: LastStep = lastStep
        protected set

    fun changeLastStep(lastStep: LastStep) {
        this.lastStep = lastStep
    }
}