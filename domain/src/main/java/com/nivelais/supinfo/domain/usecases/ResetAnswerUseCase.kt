package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.repositories.InterrogationRepository

class ResetAnswerUseCase(private val interrogationRepository: InterrogationRepository) :
    UseCase<Int, ResetAnswerUseCase.Params>() {

    override suspend fun run(params: Params): Data<Int> {
        return Data(Status.SUCCESSFUL, interrogationRepository.resetAnswer(params.questionId), null)
    }

    data class Params(
        val questionId : Long
    )
}