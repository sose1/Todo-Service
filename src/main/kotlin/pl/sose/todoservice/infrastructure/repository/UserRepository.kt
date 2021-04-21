package pl.sose.todoservice.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import pl.sose.todoservice.domain.entity.User

interface UserRepository: JpaRepository<User, String>