package com.nivelais.supinfo.domain.entities

/**
 * Entity representing a question to ask
 */
data class QuestionEntity(
    val id: Long,
    val text: String?,
    val type: Type
) {
    enum class Type {
        SATISFACTION,
        RATING,
        COMMENT
    }
}