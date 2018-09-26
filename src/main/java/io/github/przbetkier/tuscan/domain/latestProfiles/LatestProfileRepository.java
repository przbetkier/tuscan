package io.github.przbetkier.tuscan.domain.latestProfiles;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LatestProfileRepository extends JpaRepository<LatestProfile, String> {

    List<LatestProfile> findAllByOrderByCreatedOnDesc();
}
