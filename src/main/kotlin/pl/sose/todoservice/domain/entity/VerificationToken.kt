package pl.sose.todoservice.domain.entity

import java.util.*
import javax.persistence.*


@Entity
data class VerificationToken(
    @Id
    @Column(length = 190)
    val id: String = UUID.randomUUID().toString(),

    @Column
    val token: String,

    @OneToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    val user: User,

    @Column
    val expiryDate: Date = calculateExpiryDate(EXPIRATION)

) {
    companion object {
        const val EXPIRATION: Int = 15
    }
}

fun calculateExpiryDate(expiryTimeInMinutes: Int): Date {
    val cal = Calendar.getInstance()
    cal.timeInMillis = Date().time
    cal.add(Calendar.MINUTE, expiryTimeInMinutes)
    return Date(cal.time.time)
}