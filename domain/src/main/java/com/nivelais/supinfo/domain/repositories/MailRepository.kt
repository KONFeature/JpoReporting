package com.nivelais.supinfo.domain.repositories

import java.io.File

interface MailRepository {

    /**
     * Send the report of all the interrogation done via mail
     */
    suspend fun sendJpoReportMail(file: File)

}