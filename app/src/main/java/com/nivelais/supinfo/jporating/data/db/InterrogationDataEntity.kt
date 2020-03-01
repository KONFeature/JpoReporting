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
     * Name of the person answering,optional
     */
    val name: String?,

    /**
     * Age of the person answering, optional
     */
    val age: Int?,

    /**
     * Start time of the interrogation
     */
    val start: Date,

    /**
     * End time of the interrogation
     */
    val end: Date?
) {
    /**
     * All the answers
     */
    lateinit var answers: ToMany<AnswerDataEntity>
}