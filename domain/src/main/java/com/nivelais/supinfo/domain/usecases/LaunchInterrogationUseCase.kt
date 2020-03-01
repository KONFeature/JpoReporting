package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.entities.InterrogationEntity
import com.nivelais.supinfo.domain.repositories.InterrogationRepository

class LaunchInterrogationUseCase(val interrogationRepository: InterrogationRepository) :
        UseCase<InterrogationEntity, LaunchInterrogationUseCase.Params>() {

    override suspend fun run(params: Params): Data<InterrogationEntity> {
        return Data(Status.SUCCESSFUL, interrogationRepository.launch(params.name, params.age), null)
    }

    data class Params(
        val name: String?,
        val age: Int?
    )
}