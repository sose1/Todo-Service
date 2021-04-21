package pl.sose.todoservice.application.model.request

data class CreateUserRequest(
    val name: String,
    val email: String,
    val password: String,
)
