package com.nivelais.supinfo.domain.repositories

import com.nivelais.supinfo.domain.entities.AnswerEntity

interface AnswerRepository {

    suspend fun updateAnswer(answerEntity: AnswerEntity)

}