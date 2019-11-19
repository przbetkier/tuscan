package io.github.przbetkier.tuscan.domain.match;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MatchRepository extends ReactiveMongoRepository<Match, String> {}
