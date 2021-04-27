package pl.sose.todoservice.application.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import pl.sose.todoservice.application.model.request.CreateUserRequest
import pl.sose.todoservice.application.model.response.UserResponse
import pl.sose.todoservice.domain.entity.User
import pl.sose.todoservice.domain.model.UserCreateDTO
import pl.sose.todoservice.domain.service.UserService
import java.util.*

internal class UserControllerTest {
    private val userService = mockk<UserService>()
    private val userController = UserController(userService)

    @Nested
    inner class Create {
        @Test
        fun `should return user response on valid request`() {
            //given
            val createUserRequest = createUserRequest()
            val id = UUID.randomUUID().toString()
            val slot = slot<UserCreateDTO>()

            every { userService.createUser(capture(slot)) } returns
                User(id, createUserRequest.name, createUserRequest.email, createUserRequest.password)

            //when
            val result = userController.createUser(createUserRequest)

            //then
            verify(exactly = 1) { userService.createUser(slot.captured) }
            assertEquals(
                UserResponse(
                    id = id,
                    name = createUserRequest.name,
                    email = createUserRequest.email),
                result)
        }
    }

    @Nested
    inner class GetById {
        @Test
        fun `should return user response when id is correct`() {
            //given
            val id = UUID.randomUUID().toString()
            val createUserRequest= createUserRequest()
            val user = User(id, createUserRequest.name, createUserRequest.email, createUserRequest.password)

            every { userService.getUserById(id) } returns user

            //when
            val result = userController.getUserById(id)

            //then
            verify(exactly = 1) { userService.getUserById(id) }
            assertEquals(UserResponse(
                id = id,
                name = createUserRequest.name,
                email = createUserRequest.email
            ), result)
        }
    }
}

fun createUserRequest(name: String = "Jacob",
                      email:String = "mail@gmail.com",
                      password:String = "!Password1"
) = CreateUserRequest(name, email, password)