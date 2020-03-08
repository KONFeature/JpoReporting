package com.nivelais.supinfo.jporating.data.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class QuestionDataEntity(
    @Id
    var id: Long = 0,

    /**
     * The order of the question
     */
    val position: Int,

    /**
     * Text of the question
     */
    val text: String,

    /**
     * Type of the question
     */
    val type: Int
) {
}