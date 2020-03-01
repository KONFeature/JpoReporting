package com.nivelais.supinfo.jporating.data.db

import com.nivelais.supinfo.domain.entities.QuestionEntity
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class QuestionDataEntity(
    @Id
    var id: Long = 0,

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