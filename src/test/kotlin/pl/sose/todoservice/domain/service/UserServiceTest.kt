package pl.sose.todoservice.domain.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
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
    @Nested
    inner class GetById {
        @Test
        fun `should return user when id is correct`() {
            //given
            val id = UUID.randomUUID().toString()
            val userCreateDTO = userCreateDTO()
            val user = User(id, userCreateDTO.name, userCreateDTO.email, userCreateDTO.password)

            every { userRepository.findByIdOrNull(id) } returns user

            //when
            val result = userService.getUserById(id)

            //then
            verify(exactly = 1) { userRepository.findByIdOrNull(id) }
            assertEquals(user, result)
        }

        @Test
        fun `should throw when id is incorrect`() {
            //given
            every { userRepository.findByIdOrNull("Bad id") } throws ResponseStatusException(HttpStatus.NOT_FOUND)

            //when
            //then
            assertThrows(ResponseStatusException::class.java) {
                userService.getUserById("Bad id")
            }

        }
    }
}

fun userCreateDTO(
    name: String = "Jacob",
    email:String = "mail@gmail.com",
    password:String = "!Password1"
) = UserCreateDTO(name, email, password)