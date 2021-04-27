package pl.sose.todoservice.domain.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pl.sose.todoservice.application.model.response.UserResponse
import pl.sose.todoservice.domain.entity.User
import pl.sose.todoservice.domain.model.UserCreateDTO
import pl.sose.todoservice.infrastructure.repository.UserRepository

@Service
class UserService(private val userRepository: UserRepository) {
    fun createUser(userCreateDTO: UserCreateDTO): User {
        return userRepository.save(
            User(
                name = userCreateDTO.name,
                email = userCreateDTO.email,
                password = userCreateDTO.password)
        )
    }

    fun getUserById(id: String): User {
        return userRepository.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with this ID = $id not found")
    }

    fun deleteUser(id: String) {
        val user = userRepository.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with this ID = $id not found")

        userRepository.delete(user)
    }
}