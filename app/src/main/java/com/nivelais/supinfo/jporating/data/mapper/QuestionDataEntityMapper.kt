package com.nivelais.supinfo.jporating.data.mapper

import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.jporating.data.db.QuestionDataEntity

class QuestionDataEntityMapper : Mapper<QuestionDataEntity, QuestionEntity>() {

    override fun map(from: QuestionDataEntity): QuestionEntity =
        QuestionEntity(
            from.id,
            from.text,
            QuestionEntity.Type.fromId(from.type) ?: QuestionEntity.Type.SATISFACTION
        )
}