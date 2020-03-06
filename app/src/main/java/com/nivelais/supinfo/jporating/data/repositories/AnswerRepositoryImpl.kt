package com.nivelais.supinfo.jporating.data.repositories

import com.nivelais.supinfo.domain.entities.AnswerEntity
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.domain.repositories.AnswerRepository
import com.nivelais.supinfo.domain.repositories.QuestionRepository
import com.nivelais.supinfo.jporating.data.db.AnswerDataEntity
import com.nivelais.supinfo.jporating.data.db.QuestionDataEntity
import com.nivelais.supinfo.jporating.data.mapper.AnswerDataEntityMapper
import com.nivelais.supinfo.jporating.data.mapper.QuestionDataEntityMapper
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

class AnswerRepositoryImpl(
    boxStore: BoxStore
) : AnswerRepository {

}