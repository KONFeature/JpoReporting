package com.nivelais.supinfo.domain.entities

/**
 * Entity representing a question to ask
 */
data class QuestionEntity(
    val id: Long,
    val text: String?,
    val type: Type
) {
    enum class Type(val id: Int) {
        SATISFACTION(0),
        RATING(1),
        COMMENT(2);

        companion object {
            fun fromId(id: Int) =
                Type.values()
                    .firstOrNull() { it.id == id }
        }
    }
}