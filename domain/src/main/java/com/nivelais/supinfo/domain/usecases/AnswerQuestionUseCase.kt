package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.entities.AnswerEntity
import com.nivelais.supinfo.domain.repositories.AnswerRepository
import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import com.nivelais.supinfo.domain.repositories.QuestionRepository

class AnswerQuestionUseCase(
    private val questionRepository: QuestionRepository,
    private val interrogationRepository: InterrogationRepository
) :
    UseCase<Unit, AnswerQuestionUseCase.Params>() {

    override suspend fun run(params: Params): Data<Unit> {
        // Retreive the question answered
        val question = questionRepository.get(params.questionId)

        // Create the answer
        val answer = AnswerEntity(rating = params.rating, question = question)

        // Add it to the interrogation
        interrogationRepository.addAnswer(answer)

        return Data(
            Status.SUCCESSFUL,
            null,
            null
        )
    }

    data class Params(
        val questionId: Long,
        val rating: Int
    )
}