package com.nivelais.supinfo.jporating.data.mapper

import com.nivelais.supinfo.domain.entities.InterrogationEntity

class InterrogationEntityCsvStringMapper : Mapper<InterrogationEntity, Array<String>>() {

    companion object {
        /**
         * The base number of column we want in our final csv string
         */
        private const val BASE_COLUMN_COUNT = 2
    }

    override fun map(from: InterrogationEntity): Array<String> {

        // Find and order all the rating
        val ratings = from.answers
            .sortedBy {
                it.question.position
            }
            .map { it.rating.toString() }

        // Create the final array
        return Array(BASE_COLUMN_COUNT + ratings.size) { position ->
            when(position) {
                0 -> from.start.toString()
                1 -> from.end?.toString()?:"Not finished"
                else -> ratings.elementAtOrElse(position - BASE_COLUMN_COUNT) { "null" }
            }
        }
    }

    /**
     * Get the header for the CSV File
     */
    fun getCsvHeader() : Array<String> {
        return Array(BASE_COLUMN_COUNT + 6) { position ->
            when(position) {
                0 -> "Debut"
                1 -> "Fin"
                else -> "Question ${position - BASE_COLUMN_COUNT}"
            }
        }
    }
}