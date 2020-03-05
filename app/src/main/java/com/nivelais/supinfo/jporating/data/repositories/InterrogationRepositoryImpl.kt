package com.nivelais.supinfo.jporating.data.repositories

import com.nivelais.supinfo.domain.entities.AnswerEntity
import com.nivelais.supinfo.domain.entities.InterrogationEntity
import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import com.nivelais.supinfo.jporating.data.db.AnswerDataEntity
import com.nivelais.supinfo.jporating.data.db.InterrogationDataEntity
import com.nivelais.supinfo.jporating.data.db.QuestionDataEntity
import com.nivelais.supinfo.jporating.data.mapper.InterrogationDataEntityMapper
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import java.util.*

class InterrogationRepositoryImpl(
    boxStore: BoxStore
) : InterrogationRepository {

    /**
     * Access to our database
     */
    private val dao: Box<InterrogationDataEntity> = boxStore.boxFor()

    /**
     * Access to our database
     */
    private val questionDao: Box<QuestionDataEntity> = boxStore.boxFor()

    /**
     * Mapper for our database entity
     */
    private val entityMapper = InterrogationDataEntityMapper()

    /**
     * The current interrogation (if any)
     */
    private var currentInterrogation: InterrogationDataEntity? = null

    /**
     * Launch an interrogation
     */
    override fun launch(name: String?, age: Int?): InterrogationEntity {
        // Create the interrogation and save it
        val interrogation =
            InterrogationDataEntity(start = Date(), end = null)
        dao.put(interrogation)
        currentInterrogation = interrogation

        return entityMapper.map(interrogation)
    }

    override fun answer(questionId: Long, rating: Int) : Int {
        // Check if we have a current interrrogation
        if (currentInterrogation == null) {
            val interrogation =
                InterrogationDataEntity(start = Date(), end = null)
            dao.put(interrogation)
            currentInterrogation = interrogation
        }

        // Find the question
        val question = questionDao.get(questionId)

        // Check if the questions was already answered before
        val prevAnswer = currentInterrogation?.answers?.firstOrNull { answer ->
            answer.question.target.id == questionId
        }

        // If we have a previus answer we update it's rating
        if(prevAnswer != null) {
            prevAnswer.rating = rating

            // We back it up
            dao.put(currentInterrogation!!)
        } else {
            // Create answer
            val answer = AnswerDataEntity(rating = rating).apply {
                this.question.target = question
            }

            // Add the answer to the interrogation and refresh it
            currentInterrogation?.apply {
                answers.add(answer)
                dao.put(this)
            }
        }

        return currentInterrogation?.answers?.count()?:0
    }

    override fun resetAnswer(questionId: Long) : Int {
        currentInterrogation?.let { interrogation ->
            // Find the answer to remove
            interrogation.answers.removeIf { answer ->
                answer.question.target.id == questionId
            }

            // Update it in database
            dao.put(interrogation)
        }

        return currentInterrogation?.answers?.count()?:0
    }

    override fun finish() {
        currentInterrogation?.let {
            // Put the end date and update it
            it.end = Date()
            dao.put(it)
        }
    }
}