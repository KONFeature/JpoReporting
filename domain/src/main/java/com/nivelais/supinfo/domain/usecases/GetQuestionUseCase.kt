package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.domain.repositories.QuestionRepository

class GetQuestionUseCase(val questionRepository: QuestionRepository) :
    UseCase<Set<QuestionEntity>, Unit>() {

    override suspend fun run(params: Unit): Data<Set<QuestionEntity>> {
        return Data(Status.SUCCESSFUL, questionRepository.get(), null)
    }

}