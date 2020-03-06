package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.repositories.InterrogationRepository

class ResetAnswerUseCase(private val interrogationRepository: InterrogationRepository) :
    UseCase<Unit, ResetAnswerUseCase.Params>() {

    override suspend fun run(params: Params): Data<Unit> {
        // Try to fetch the answered question
        val answer = interrogationRepository.getAnswer(params.questionId)
        answer?.let {
            // If it's founded we remove it
            interrogationRepository.removeAnswer(answerId = it.id)
        }

        return Data(Status.SUCCESSFUL, null, null)
    }

    data class Params(
        val questionId: Long
    )
}