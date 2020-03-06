package com.nivelais.supinfo.domain.entities

/**
 * Entity representing a user answer to a question
 */
data class AnswerEntity(
    val id: Long = 0,
    var rating: Int?,
    val question: QuestionEntity
)