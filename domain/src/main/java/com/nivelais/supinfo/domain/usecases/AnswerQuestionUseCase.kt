package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.entities.AnswerEntity
import com.nivelais.supinfo.domain.repositories.AnswerRepository
import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import com.nivelais.supinfo.domain.repositories.QuestionRepository

class AnswerQuestionUseCase(
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
    private val interrogationRepository: InterrogationRepository
) :
    UseCase<Unit, AnswerQuestionUseCase.Params>() {

    override suspend fun run(params: Params): Data<Unit> {
        // Retreive the question answered
        val question = questionRepository.get(params.questionId)

        // Check if we already have an answer for this question
        val answer = interrogationRepository.getAnswerForQuestion(question.id)

        answer?.let {
            // Update the answer rating
            answer.rating = params.rating
            answerRepository.updateAnswer(answer)
        }?:let {
            // Create a new answer and add it to the current interrogation
            val newAnswer = AnswerEntity(rating = params.rating, question = question)
            interrogationRepository.addAnswer(newAnswer)
        }

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