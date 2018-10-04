package io.github.przbetkier.tuscan.domain.latestProfiles;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LatestProfileRepository extends MongoRepository<LatestProfile, String> {

    List<LatestProfile> findAllByOrderByCreatedOnDesc();
}
