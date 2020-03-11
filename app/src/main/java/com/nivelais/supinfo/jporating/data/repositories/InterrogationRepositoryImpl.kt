package com.nivelais.supinfo.jporating.data.repositories

import com.nivelais.supinfo.domain.entities.AnswerEntity
import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import com.nivelais.supinfo.jporating.data.db.AnswerDataEntity
import com.nivelais.supinfo.jporating.data.db.InterrogationDataEntity
import com.nivelais.supinfo.jporating.data.db.InterrogationDataEntity_
import com.nivelais.supinfo.jporating.data.mapper.InterrogationDataEntityMapper
import com.nivelais.supinfo.jporating.data.mapper.InterrogationEntityCsvStringMapper
import com.opencsv.CSVWriterBuilder
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import java.io.File
import java.io.FileWriter
import java.util.*
import java.util.stream.Collectors

class InterrogationRepositoryImpl(
    boxStore: BoxStore,
    internalFolder: File
) : InterrogationRepository {

    /**
     * Folder in wich all the csv file will be dropped
     */
    private val csvFolder = File(internalFolder, "report")

    /**
     * Access to our database
     */
    private val dao: Box<InterrogationDataEntity> = boxStore.boxFor()

    /**
     * Mapper for our database entity
     */
    private val entityMapper = InterrogationDataEntityMapper()

    /**
     * Mapper for our database entity
     */
    private val csvMapper = InterrogationEntityCsvStringMapper()

    /**
     * The current interrogation (if any)
     */
    private var currentInterrogation: InterrogationDataEntity? = null

    /**
     * Listener on the number of answered questions
     */
    private var answeredQuestionChannel: Channel<Int>? = null

    override suspend fun launch(): ReceiveChannel<Int> {
        // If we have a current interrogation we close it
        currentInterrogation?.let {
            finish()
        }

        // We create a new interrogation
        val interrogation =
            InterrogationDataEntity(start = Date(), end = null)
        dao.put(interrogation)
        currentInterrogation = interrogation

        // We create the channel that will receive all the update
        answeredQuestionChannel = Channel()

        // Send the listener
        return answeredQuestionChannel!!
    }

    override suspend fun addAnswer(answer: AnswerEntity) {
        currentInterrogation?.let { interrogation ->
            // Create a new answer
            val answerData = AnswerDataEntity(rating = answer.rating).apply {
                question.targetId = answer.question.id
            }
            // Add it to the interrogation
            interrogation.answers.add(answerData)
            // Update the interrogation
            interrogation.answers.applyChangesToDb()
        } ?: kotlin.run {
            // Create the interrogation and relaunch this operation
            launch()
            addAnswer(answer)
        }

        // Refresh the listener
        updateAnsweredCount()
    }

    override suspend fun removeAnswer(answerId: Long) {
        currentInterrogation?.let { interrogation ->
            // Remove the answer and update interrogation
            interrogation.answers.setRemoveFromTargetBox(true)
            interrogation.answers.removeById(answerId)
            interrogation.answers.applyChangesToDb()
        }

        // Refresh the listener
        updateAnsweredCount()
    }

    override suspend fun getAnswerForQuestion(questionId: Long): AnswerEntity? {
        if (currentInterrogation == null) return null

        // Refresh current interrogation
        currentInterrogation = dao.get(currentInterrogation!!.id)
        // Try to find the answer
        return entityMapper
            .map(currentInterrogation!!)
            .answers
            .firstOrNull { answerEntity ->
                answerEntity.question.id == questionId
            }
    }

    override suspend fun removeAll() {
        TODO("Not yet implemented")
    }

    override suspend fun finish() {
        currentInterrogation?.let {
            // Check the user have answered all the question, else we don't persist it
            if (it.answers.size < 6) {
                dao.remove(it.id)
                return
            }

            // Put the end date and update it
            it.end = Date()
            dao.put(it)
        }

        // Remove the interrogation from here
        currentInterrogation = null

        // Close the listener and reset it
        answeredQuestionChannel?.close()
        answeredQuestionChannel = null
    }

    override suspend fun generateCsvRecap(): File {
        if (!csvFolder.exists()) csvFolder.mkdirs()

        // Create all the interrogation to print to the csv file
        val interrogationsCsv = dao.query()
            // DB Query
            .notNull(InterrogationDataEntity_.end)
            .build()
            .find()
            // Stream conversions
            .stream()
            .map { interrogationData ->
                // Find and order all the rating
                csvMapper.map(entityMapper.map(interrogationData))
            }.collect(Collectors.toList())

        // Create the writer who will write the file
        val csvFile = File(csvFolder, "JpoReport_${Date().time}.csv")
        val writer = CSVWriterBuilder(FileWriter(csvFile))
            .withSeparator(';')
            .build()


        // Write to the file
        writer.writeNext(csvMapper.getCsvHeader())
        writer.writeAll(interrogationsCsv)
        writer.close()

        // Send the generated file as result
        return csvFile
    }

    private suspend fun updateAnsweredCount() {
        answeredQuestionChannel?.send(currentInterrogation?.answers?.size ?: 0)
    }
}