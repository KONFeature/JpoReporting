package com.nivelais.supinfo.domain.usecases

import com.nivelais.supinfo.domain.common.Data
import com.nivelais.supinfo.domain.common.Status
import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import com.nivelais.supinfo.domain.repositories.MailRepository

class SendInterrogationsUseCase(
    private val interrogationRepository: InterrogationRepository,
    private val mailRepository: MailRepository
) : UseCase<Unit, Unit>() {

    override suspend fun run(params: Unit): Data<Unit> {
        // Generate the CSV file
        interrogationRepository.generateCsvRecap()?.let { recapFile ->
            // Send The generated recap file via mail
            mailRepository.sendJpoReportMail(recapFile)

            // Delete the generated file when the mail is sent
            recapFile.delete()
        }

        return Data(Status.SUCCESSFUL, null, null)
    }
}