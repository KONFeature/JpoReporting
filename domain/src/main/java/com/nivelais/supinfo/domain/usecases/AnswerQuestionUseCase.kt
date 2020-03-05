package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.repositories.InterrogationRepository

class AnswerQuestionUseCase(private val interrogationRepository: InterrogationRepository) :
    UseCase<Int, AnswerQuestionUseCase.Params>() {

    override suspend fun run(params: Params): Data<Int> {
        return Data(Status.SUCCESSFUL, interrogationRepository.answer(params.questionId, params.rating), null)
    }

    data class Params(
        val questionId : Long,
        val rating: Int
    )
}