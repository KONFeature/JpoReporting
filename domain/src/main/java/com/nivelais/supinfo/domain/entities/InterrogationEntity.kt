package com.nivelais.supinfo.domain.entities

/**
 * Entity representing a user answer to a question
 */
data class InterrogationEntity(
    val id: Long,
    val questions: HashMap<QuestionEntity, AnswerEntity?> = HashMap()
)