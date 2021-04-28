package pl.sose.todoservice.application.mailVerification

import org.springframework.context.ApplicationListener
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import pl.sose.todoservice.domain.entity.User
import pl.sose.todoservice.domain.service.UserService
import java.util.*


@Component
class RegistrationListener(private val userService: UserService,
                           private val mailSender: JavaMailSender
) : ApplicationListener<OnRegistrationCompleteEvent> {

    override fun onApplicationEvent(event: OnRegistrationCompleteEvent) {
        confirmRegistration(event)
    }

    private fun confirmRegistration(event: OnRegistrationCompleteEvent) {
        val user: User = event.user
        val token = UUID.randomUUID().toString()

        userService.createVerificationToken(user, token)

        val recipientAddress: String = user.email
        val subject = "Verify your account"
        val confirmationUrl = "${event.appUrl}/registrationConfirm?token=$token"
        val email = SimpleMailMessage()

        email.setTo(recipientAddress)
        email.setSubject(subject)
        email.setText("http://localhost:8080$confirmationUrl", )

        mailSender.send(email)
    }
}