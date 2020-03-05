package com.nivelais.supinfo.domain.repositories

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.entities.AnswerEntity
import com.nivelais.supinfo.domain.entities.InterrogationEntity

interface InterrogationRepository {

    /**
     * Launch a new interrogation
     */
    fun launch(name: String?, age: Int?) : InterrogationEntity

    /**
     * Add an answer to an interrogation
     * Return the number of questions answered in the interrogation
     */
    fun answer(questionId: Long, rating: Int) : Int

    /**
     * Reset an answer to an interrogation
     * Return the number of questions answered in the interrogation
     */
    fun resetAnswer(questionId: Long) : Int

    /**
     * Finish the current interrogation
     */
    fun finish()

}