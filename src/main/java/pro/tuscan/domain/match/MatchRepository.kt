package pro.tuscan.domain.match

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository: ReactiveMongoRepository<Match, String>
