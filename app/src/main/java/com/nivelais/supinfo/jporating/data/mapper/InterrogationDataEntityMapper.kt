package com.nivelais.supinfo.jporating.data.mapper

import com.nivelais.supinfo.domain.entities.InterrogationEntity
import com.nivelais.supinfo.jporating.data.db.InterrogationDataEntity

class InterrogationDataEntityMapper : Mapper<InterrogationDataEntity, InterrogationEntity>() {

    private val answerMapper = AnswerDataEntityMapper()

    override fun map(from: InterrogationDataEntity): InterrogationEntity = InterrogationEntity(
        from.id,
        from.name,
        from.age,
        answerMapper.mapListToSet(from.answers)
    )
}