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
    var rating: Int?
) {

    constructor(id: Long, rating: Int?, questionId: Long) : this(id, rating) {
        question.targetId = questionId
    }

    /**
     * The question answered
     */
    lateinit var question: ToOne<QuestionDataEntity>
}