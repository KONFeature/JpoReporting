package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import com.nivelais.supinfo.domain.repositories.QuestionRepository
import kotlinx.coroutines.channels.ReceiveChannel

class LaunchInterrogationUseCase(
    private val questionRepository: QuestionRepository,
    private val interrogationRepository: InterrogationRepository
) :
    UseCase<LaunchInterrogationUseCase.Result, Unit>() {

    override suspend fun run(params: Unit): Data<Result> {
        // Find all the questions
        val questions = questionRepository.get()

        // Init the interrogation and fetch the receive channel
        val channel = interrogationRepository.launch()

        // Send the i nit result
        return Data(Status.SUCCESSFUL, Result(questions, channel), null)
    }

    data class Result(
        val questions: Set<QuestionEntity>,
        val answersCountLive: ReceiveChannel<Int>
    )
}