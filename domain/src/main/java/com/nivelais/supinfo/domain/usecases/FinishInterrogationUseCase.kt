package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.repositories.InterrogationRepository

class FinishInterrogationUseCase(private val interrogationRepository: InterrogationRepository) :
    UseCase<Unit, Unit>() {

    override suspend fun run(params: Unit): Data<Unit> {
        interrogationRepository.finish()
        return Data(Status.SUCCESSFUL, null, null)
    }
}