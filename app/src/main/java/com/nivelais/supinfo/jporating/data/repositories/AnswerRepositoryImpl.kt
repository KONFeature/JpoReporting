package com.nivelais.supinfo.jporating.data.repositories

import com.nivelais.supinfo.domain.entities.AnswerEntity
import com.nivelais.supinfo.domain.repositories.AnswerRepository
import com.nivelais.supinfo.jporating.data.db.AnswerDataEntity
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

class AnswerRepositoryImpl(
    boxStore: BoxStore
) : AnswerRepository {

    /**
     * Access to our database
     */
    private val dao: Box<AnswerDataEntity> = boxStore.boxFor()

    override suspend fun updateAnswer(answerEntity: AnswerEntity) {
        // Update an answer we have in database
        val answer = dao.get(answerEntity.id)
        answer.rating = answerEntity.rating
        dao.put(answer)
    }

}