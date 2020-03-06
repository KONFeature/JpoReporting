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

    override suspend fun get(): Set<QuestionEntity> {
        var questions = dao.all
        if(questions.size <= 0) {
            dao.put(QuestionDataEntity(text = "Avez-vous été bien accueilli(e- s) à votre arrivée ?", type = 0, position = 1))
            dao.put(QuestionDataEntity(text = "L'étudiant(e) qui vous accompagnait a-t-il(elle) facilité votre visite ?", type = 0, position = 2))
            dao.put(QuestionDataEntity(text = "Pôle Projet : les projets présentés vous ont-ils permis de visualiser ce que nous étudions ?", type = 0, position = 3))
            dao.put(QuestionDataEntity(text = "Conférence générale : a-t-elle abordé les points essentiels vous permettant de faire votre choix d’école ?", type = 0, position = 4))
            dao.put(QuestionDataEntity(text = "Conférence entreprises : vous a-t-elle permis de mesurer notre partenariat avec les professionnels du secteur ?", type = 0, position = 5))
            questions = dao.all
        }
        return entityMapper.mapList(questions).toHashSet()
    }

    override suspend fun get(questionId: Long) = entityMapper.map(dao.get(questionId))
}