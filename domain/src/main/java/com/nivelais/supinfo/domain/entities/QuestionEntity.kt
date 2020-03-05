package com.nivelais.supinfo.domain.entities

/**
 * Entity representing a question to ask
 */
data class QuestionEntity(
    val id: Long,
    val text: String?,
    val position: Int,
    val type: Type
) {
    enum class Type(val id: Int) {
        SMILEY(0),
        TO_TEN(1);

        companion object {
            fun fromId(id: Int) =
                Type.values()
                    .firstOrNull() { it.id == id }
        }
    }
}