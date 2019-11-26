package io.github.przbetkier.tuscan.domain.stats;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface DemoStatsRepository extends ReactiveMongoRepository<DemoStats, String> {}
