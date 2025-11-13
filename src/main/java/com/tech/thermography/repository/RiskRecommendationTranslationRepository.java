package com.tech.thermography.repository;

import com.tech.thermography.domain.RiskRecommendationTranslation;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RiskRecommendationTranslation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskRecommendationTranslationRepository extends JpaRepository<RiskRecommendationTranslation, UUID> {}
