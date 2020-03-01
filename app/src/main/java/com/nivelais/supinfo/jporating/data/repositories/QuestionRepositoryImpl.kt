package com.nivelais.supinfo.jporating.data.repositories

import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.domain.repositories.QuestionRepository
import com.nivelais.supinfo.jporating.data.db.QuestionDataEntity
import com.nivelais.supinfo.jporating.data.mapper.QuestionDataEntityMapper
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

class QuestionRepositoryImpl(
    boxStore: BoxStore
) : QuestionRepository {

    /**
     * Access to our database
     */
    private val dao: Box<QuestionDataEntity> = boxStore.boxFor()

    /**
     * Mapper for our database entity
     */
    private val entityMapper = QuestionDataEntityMapper()

    override fun create(question: QuestionEntity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(): Set<QuestionEntity> {
        val questions = dao.all
        if(questions.size <= 0) {
            dao.put(QuestionDataEntity(text = "Simple first question ?", type = 0))
            dao.put(QuestionDataEntity(text = "Simple second question ?", type = 1))
            dao.put(QuestionDataEntity(text = "Simple third question ?", type = 2))
            dao.put(QuestionDataEntity(text = "Simple fourth question ?", type = 2))
            dao.put(QuestionDataEntity(text = "Simple fith question ?", type = 2))
        }
        return entityMapper.mapList(questions).toHashSet()
    }
}