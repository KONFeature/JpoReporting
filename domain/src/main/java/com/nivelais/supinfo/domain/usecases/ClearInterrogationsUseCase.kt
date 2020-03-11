package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import com.nivelais.supinfo.domain.repositories.MailRepository

class ClearInterrogationsUseCase(
    private val interrogationRepository: InterrogationRepository
) : UseCase<Unit, Unit>() {

    override suspend fun run(params: Unit): Data<Unit> {
        // Remove all the interrogations
        interrogationRepository.removeAll()

        return Data(Status.SUCCESSFUL, null, null)
    }
}