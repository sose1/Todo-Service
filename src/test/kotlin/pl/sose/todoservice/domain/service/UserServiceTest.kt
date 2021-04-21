package pl.sose.todoservice.domain.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import pl.sose.todoservice.domain.entity.User
import pl.sose.todoservice.domain.model.UserCreateDTO
import pl.sose.todoservice.infrastructure.repository.UserRepository
import java.util.*

internal class UserServiceTest {
    private val userRepository = mockk<UserRepository>()
    private val userService = UserService(userRepository)

    @Nested
    inner class Create {
        @Test
        fun `should return user on valid userDTO`() {
            //given
            val userCreateDTO = userCreateDTO()
            val id = UUID.randomUUID().toString()
            val slot = slot<User>()
            every { userRepository.save(capture(slot)) } returns
                User(
                    id = id,
                    name = userCreateDTO.name,
                    email = userCreateDTO.email,
                    password = userCreateDTO.password,
                )
            //when
            val result = userService.createUser(userCreateDTO)

            //then
            verify(exactly = 1) { userRepository.save(slot.captured) }
            assertEquals(
                User(
                    id = id,
                    name = userCreateDTO.name,
                    email = userCreateDTO.email,
                    password = userCreateDTO.password,
                ), result)
        }
    }
}

fun userCreateDTO(
    name: String = "Jacob",
    email:String = "mail@gmail.com",
    password:String = "!Password1"
) = UserCreateDTO(name, email, password)