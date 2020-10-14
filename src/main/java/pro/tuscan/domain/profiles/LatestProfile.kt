package pro.tuscan.domain.profiles

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.Instant

@Document(collection = "latestProfiles")
data class LatestProfile(@Id val nickname: String,
                         val avatarUrl: String,
                         val level: Number,
                         val elo: Number,
                         val kdRatio: BigDecimal,
                         val createdOn: Instant)
