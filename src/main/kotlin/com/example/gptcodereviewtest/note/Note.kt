package com.example.gptcodereviewtest.note

import java.time.LocalDateTime

data class Note(
    var id: Long? = null,
    var title: String? = null,
    var content: String? = null,
    var author: String? = null,
    var password: String? = null,
    var deleted: Boolean = false,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null,
)
