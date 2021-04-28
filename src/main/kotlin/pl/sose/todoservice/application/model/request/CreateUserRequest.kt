package pl.sose.todoservice.application.model.request

import pl.sose.todoservice.domain.validators.ValidEmail
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CreateUserRequest(
    @NotNull
    @NotEmpty
    val name: String,

    @ValidEmail
    @NotNull
    @NotEmpty
    val email: String,

    @NotNull
    @NotEmpty
    val password: String,
)
