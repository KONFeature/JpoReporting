package com.nivelais.supinfo.domain.repositories

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.entities.QuestionEntity

interface QuestionRepository {

    /**
     * Create a new question
     */
    fun create(question: QuestionEntity)

    /**
     * Get all the questions
     */
    fun get() : Set<QuestionEntity>

    /**
     * Retreive a question from it's id
     */
    fun get(questionId: Long) : QuestionEntity

}