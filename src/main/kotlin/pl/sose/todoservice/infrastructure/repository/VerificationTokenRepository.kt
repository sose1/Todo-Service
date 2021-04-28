package pl.sose.todoservice.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import pl.sose.todoservice.domain.entity.VerificationToken

interface VerificationTokenRepository: JpaRepository<VerificationToken, String> {
    fun findByToken(token: String): VerificationToken
}