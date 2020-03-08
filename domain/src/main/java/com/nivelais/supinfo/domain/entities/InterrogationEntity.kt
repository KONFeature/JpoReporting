package com.nivelais.supinfo.domain.entities

import java.util.*

/**
 * Entity representing a user answer to a question
 */
data class InterrogationEntity(
    val id: Long,
    val start: Date,
    var end: Date?,
    val answers: Set<AnswerEntity>
)