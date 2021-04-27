package pl.sose.todoservice.application.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.sose.todoservice.application.model.request.CreateUserRequest
import pl.sose.todoservice.application.model.response.UserResponse
import pl.sose.todoservice.domain.model.UserCreateDTO
import pl.sose.todoservice.domain.service.UserService
import javax.validation.Valid

@RestController
@RequestMapping("/v1")
class UserController(private val userService: UserService){

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    fun createUser(@Valid  @RequestBody request: CreateUserRequest): UserResponse {
        val userDTO = UserCreateDTO(request.name, request.email, request.password)
        val user = userService.createUser(userDTO)

        return UserResponse(
            id = user.id,
            name = user.name,
            email = user.email
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{id}")
    fun getUserById(@PathVariable id: String): UserResponse {
        val user = userService.getUserById(id)
        return UserResponse(
            id = user.id,
            name = user.name,
            email = user.email
        )
    }
}