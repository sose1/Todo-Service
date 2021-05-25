package pl.sose.todoservice.application.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import pl.sose.todoservice.domain.entity.User
import pl.sose.todoservice.domain.service.UserService
import java.util.*


@RestController
@RequestMapping("/v1")
class RegistrationController(private val userService: UserService) {

    @GetMapping("/registrationConfirm")
    fun confirmRegistration(@RequestHeader("token") token: String): User {
        val verificationToken = userService.getVerificationToken(token)

        val user = verificationToken.user
        val cal = Calendar.getInstance()

        if (verificationToken.expiryDate.time - cal.time.time <= 0) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }

        return userService.saveRegisteredUser(user)
    }
}