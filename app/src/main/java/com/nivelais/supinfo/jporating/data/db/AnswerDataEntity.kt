package com.nivelais.supinfo.jporating.data.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class AnswerDataEntity(
    @Id
    var id: Long = 0,

    /**
     * The rating, if needed
     */
    val rating: Int?,

    /**
     * Optional comment on the question / answer if comment question
     */
    val comment: String?
) {
    /**
     * The question answered
     */
    lateinit var question: ToOne<QuestionDataEntity>
}