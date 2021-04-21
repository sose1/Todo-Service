package pl.sose.todoservice.domain.service

import org.springframework.stereotype.Service
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
}