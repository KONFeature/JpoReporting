package com.nivelais.supinfo.domain.repositories

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.entities.AnswerEntity
import com.nivelais.supinfo.domain.entities.InterrogationEntity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

interface InterrogationRepository {

    /**
     * Launch a new interrogation
     */
    suspend fun launch() : ReceiveChannel<Int>

    /**
     * Add an answer to the current interrogation
     */
    suspend fun addAnswer(answer: AnswerEntity)

    /**
     * Remove an answer from the current interrogation
     */
    suspend fun removeAnswer(answerId: Long)

    /**
     * Get the answer to a question from the current interrogation
     */
    suspend fun getAnswerForQuestion(questionId: Long) : AnswerEntity?

    /**
     * Finish the current interrogation
     */
    suspend fun finish()

    /**
     * Generate a CSV summary of all the interrogation done on this device
     */
    suspend fun generateCsvRecap()
}