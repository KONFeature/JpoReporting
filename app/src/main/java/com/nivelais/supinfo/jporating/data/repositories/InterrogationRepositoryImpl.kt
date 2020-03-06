package com.nivelais.supinfo.jporating.data.repositories

import com.nivelais.supinfo.domain.entities.AnswerEntity
import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import com.nivelais.supinfo.jporating.data.db.AnswerDataEntity
import com.nivelais.supinfo.jporating.data.db.InterrogationDataEntity
import com.nivelais.supinfo.jporating.data.mapper.AnswerDataEntityMapper
import com.nivelais.supinfo.jporating.data.mapper.InterrogationDataEntityMapper
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import java.util.*

class InterrogationRepositoryImpl(
        boxStore: BoxStore
) : InterrogationRepository {

    /**
     * Access to our database
     */
    private val dao: Box<InterrogationDataEntity> = boxStore.boxFor()

    /**
     * Mapper for our database entity
     */
    private val entityMapper = InterrogationDataEntityMapper()

    /**
     * The current interrogation (if any)
     */
    private var currentInterrogation: InterrogationDataEntity? = null

    /**
     * Listener on the number of answered questions
     */
    private var answeredQuestionChannel: Channel<Int> = Channel()

    override suspend fun launch() {
        // If we have a current interrogation we close it
        currentInterrogation?.let {
            finish()
        }

        // We create a new interrogation
        val interrogation =
                InterrogationDataEntity(start = Date(), end = null)
        dao.put(interrogation)
        currentInterrogation = interrogation

        // Refresh the listener
        sendToListenerAnsweredCount()
    }

    override suspend fun answeredQuestionListener(): ReceiveChannel<Int> = answeredQuestionChannel

    override suspend fun addAnswer(answerEntity: AnswerEntity) {
        currentInterrogation?.let { interrogation ->
            // Attach our interrogation (preventing further error)
            dao.attach(interrogation)
            // Check if we already have an answer for this question
            interrogation.answers
                    .firstOrNull { it.question.targetId == answerEntity.question.id }
                    ?.apply {
                        // Update it's rating
                        rating = answerEntity.rating
                    } ?: kotlin.run {
                        // Create a new answer
                        val answerData = AnswerDataEntity(rating = answerEntity.rating).apply {
                            question.targetId = answerEntity.question.id
                        }
                        // Add it to the interrogation
                        interrogation.answers.add(answerData)
                    }
            // Update the interrogation
            dao.put(interrogation)
        } ?: kotlin.run {
            // Create the interrogation and relaunch this operation
            launch()
            addAnswer(answerEntity)
        }

        // Refresh the listener
        sendToListenerAnsweredCount()
    }

    override suspend fun removeAnswer(answerId: Long) {
        currentInterrogation?.let { interrogation ->
            // Remove the answer and update interrogation
            interrogation.answers.removeById(answerId)
            dao.put(interrogation)
        }

        // Refresh the listener
        sendToListenerAnsweredCount()
    }

    override suspend fun getAnswer(questionId: Long): AnswerEntity? {
        if (currentInterrogation == null) return null

        currentInterrogation?.let { dao.attach(it) }
        return entityMapper.map(currentInterrogation!!).answers.firstOrNull { answerEntity ->
            answerEntity?.question?.id == questionId
        }
    }

    override suspend fun finish() {
        currentInterrogation?.let {
            // Put the end date and update it
            it.end = Date()
            dao.put(it)
        }

        // Remove the interrogation from here
        currentInterrogation = null

        // Refresh the listener
        sendToListenerAnsweredCount()
    }

    private suspend fun sendToListenerAnsweredCount() {
        if (!answeredQuestionChannel.isClosedForSend) {
            answeredQuestionChannel.send(currentInterrogation?.answers?.size ?: 0)
        }
    }
}