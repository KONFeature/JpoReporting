package com.nivelais.supinfo.domain.repositories

import com.nivelais.supinfo.domain.entities.AnswerEntity
import kotlinx.coroutines.channels.ReceiveChannel
import java.io.File

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
     * Remove all the interrogation
     */
    suspend fun removeAll()

    /**
     * Finish the current interrogation
     */
    suspend fun finish()

    /**
     * Generate a CSV summary of all the interrogation done on this device
     */
    suspend fun generateCsvRecap() : File?
}