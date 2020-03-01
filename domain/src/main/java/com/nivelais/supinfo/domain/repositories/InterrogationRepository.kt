package com.nivelais.supinfo.domain.repositories

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.entities.InterrogationEntity

interface InterrogationRepository {

    /**
     * Launch a new interrogation
     */
    fun launch(name: String?, age: Int?) : InterrogationEntity

    /**
     * Update an interrogation
     */
    fun update(interrogation: InterrogationEntity) : Data<Void>

}