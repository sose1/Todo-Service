package pl.sose.todoservice.domain.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pl.sose.todoservice.domain.entity.User
import pl.sose.todoservice.domain.model.UserCreateDTO
import pl.sose.todoservice.infrastructure.repository.UserRepository

@Service
class UserService(private val userRepository: UserRepository) {
    fun createUser(userCreateDTO: UserCreateDTO): User {
        if (emailExist(userCreateDTO.email)) {
            throw ResponseStatusException(HttpStatus.CONFLICT,
                "Email is already taken")
        }

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

    fun updateUser(id: String, updates: Map<String?, Any?>?): User {
        val user = userRepository.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with this ID = $id not found")

        if (updates != null) {
            if (updates.containsKey("name")) user.name = updates["name"] as String
            if (updates.containsKey("email")) user.email = updates["email"] as String
            if (updates.containsKey("password")) user.password = updates["password"] as String
        }
        return userRepository.save(user)
    }

    private fun emailExist(email: String) = userRepository.findByEmail(email) != null
}