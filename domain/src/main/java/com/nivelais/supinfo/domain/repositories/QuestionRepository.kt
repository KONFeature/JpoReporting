package com.nivelais.supinfo.domain.repositories

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.entities.QuestionEntity

interface QuestionRepository {

    /**
     * Get all the questions
     */
    suspend fun get() : Set<QuestionEntity>

    /**
     * Retreive a question from it's id
     */
    suspend fun get(questionId: Long) : QuestionEntity

}