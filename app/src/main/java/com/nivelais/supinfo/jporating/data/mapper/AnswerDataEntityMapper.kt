package com.nivelais.supinfo.jporating.data.mapper

import com.nivelais.supinfo.domain.entities.AnswerEntity
import com.nivelais.supinfo.jporating.data.db.AnswerDataEntity

class AnswerDataEntityMapper : Mapper<AnswerDataEntity, AnswerEntity>() {

    private val questionMapper = QuestionDataEntityMapper()

    override fun map(from: AnswerDataEntity): AnswerEntity =
        AnswerEntity(
            from.id,
            from.rating,
            questionMapper.map(from.question.target)
        )
}