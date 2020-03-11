package com.nivelais.supinfo.jporating.data.repositories

import com.nivelais.supinfo.domain.repositories.MailRepository
import java.io.File
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

class MailRepositoryImpl : MailRepository {

    companion object {
        private const val HOST = "smtp.gmail.com"
        private const val PORT = 587
        private const val EMAIL = "jpo.reporting@gmail.com"
        private const val PASS = "achanger"

        private val RECEIVER_EMAILS = Array<Address>(3) {position ->
            when(position) {
                0 -> InternetAddress("nantes@supinfo.com")
                1 -> InternetAddress("guillaume.hyon@supinfo.com")
                else -> InternetAddress("maxime.peniguel@supinfo.com")
            }
        }
        private const val CC_RECEIVER_EMAIL = "quentin@nivelais.com"
    }

    override suspend fun sendJpoReportMail(file: File) {
        // Define properties for the connection
        val props = System.getProperties()
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.port"] = "587"

        // Create the connection session
        val session = Session.getInstance(props,
            object : javax.mail.Authenticator() {
                //Authenticating the password
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(EMAIL, PASS)
                }
            })

        // Create mail object with sender, receiver and subject
        val mail = MimeMessage(session).apply {
            setFrom(InternetAddress(EMAIL))
            addRecipients(
                Message.RecipientType.TO,
                RECEIVER_EMAILS
            )
            subject = "Compte rendu JPO."
        }
        val mailContents = MimeMultipart()
        // Create the message & add it
        mailContents.addBodyPart(
            MimeBodyPart().apply {
                setText("Email generer automatiquement, compte-rendu en PJ")
            })
        // Create the attachement & add it
        mailContents.addBodyPart(
            MimeBodyPart().apply {
                dataHandler = DataHandler(FileDataSource(file))
                fileName = file.name
            })
        // Add the content to the mail
        mail.setContent(mailContents)

        // Sending email
        session.getTransport("smtp").apply {
            connect(HOST, PORT, EMAIL, PASS)
            sendMessage(mail, mail.allRecipients)
            close()
        }
    }

}