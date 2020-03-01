package com.nivelais.supinfo.jporating.data.repositories

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.entities.AnswerEntity
import com.nivelais.supinfo.domain.entities.InterrogationEntity
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import com.nivelais.supinfo.jporating.data.db.QuestionDataEntity
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

class InterrogationRepositoryImpl(
    boxStore: BoxStore
) : InterrogationRepository {

    /**
     * Access to our database
     */
    private val dao: Box<InterrogationEntity> = boxStore.boxFor()

    override fun launch(name: String?, age: Int?): InterrogationEntity {
        return InterrogationEntity(
            0L,
            name,
            age,
            HashSet()
        )
    }

    override fun update(interrogation: InterrogationEntity): Data<Void> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}