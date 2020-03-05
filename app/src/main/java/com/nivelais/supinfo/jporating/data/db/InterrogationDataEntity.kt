package com.nivelais.supinfo.jporating.data.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import java.util.*

@Entity
data class InterrogationDataEntity(
    @Id
    var id: Long = 0,

    /**
     * Start time of the interrogation
     */
    val start: Date,

    /**
     * End time of the interrogation
     */
    var end: Date?
) {
    /**
     * All the answers
     */
    lateinit var answers: ToMany<AnswerDataEntity>
}