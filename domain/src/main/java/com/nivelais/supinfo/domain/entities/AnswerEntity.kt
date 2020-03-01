package com.nivelais.supinfo.domain.entities

/**
 * Entity representing a user answer to a question
 */
data class AnswerEntity(
    val id: Long,
    val rating: Int?,
    val comment: String?,
    val question: QuestionEntity
)