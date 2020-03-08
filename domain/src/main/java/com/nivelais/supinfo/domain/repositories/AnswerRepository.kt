package com.nivelais.supinfo.domain.repositories

import com.nivelais.supinfo.domain.entities.AnswerEntity

interface AnswerRepository {

    /**
     * Update an answer
     */
    suspend fun updateAnswer(answerEntity: AnswerEntity)

}