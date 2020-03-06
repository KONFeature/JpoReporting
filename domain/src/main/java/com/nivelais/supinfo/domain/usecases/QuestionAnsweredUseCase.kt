package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

class QuestionAnsweredUseCase(private val interrogationRepository: InterrogationRepository) :
    UseCase<ReceiveChannel<Int>, Unit>() {

    override suspend fun run(params: Unit): Data<ReceiveChannel<Int>> {
        return Data(Status.SUCCESSFUL, interrogationRepository.answeredQuestionListener(), null)
    }
}