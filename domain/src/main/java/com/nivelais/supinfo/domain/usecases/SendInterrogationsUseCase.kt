package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import com.nivelais.supinfo.domain.repositories.MailRepository
import com.nivelais.supinfo.domain.repositories.QuestionRepository
import kotlinx.coroutines.channels.ReceiveChannel

class SendInterrogationsUseCase(
    private val interrogationRepository: InterrogationRepository
) :
    UseCase<Unit, Unit>() {

    override suspend fun run(params: Unit): Data<Unit> {
        // Generate the CSV file
        interrogationRepository.generateCsvRecap()

        // Send the i nit result
        return Data(Status.SUCCESSFUL, null, null)
    }
}