package pl.sose.todoservice.domain.model

data class UserCreateDTO (
    val name: String,
    val email: String,
    val password: String,
)