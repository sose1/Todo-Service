package pl.sose.todoservice.application.mailVerification

import org.springframework.context.ApplicationEvent
import pl.sose.todoservice.domain.entity.User
import java.util.*


data class OnRegistrationCompleteEvent(
    val user: User, val locale: Locale, val appUrl: String
) : ApplicationEvent(user)