package com.tech.thermography.repository;

import com.tech.thermography.domain.InspectionRoute;
import com.tech.thermography.domain.User;
import com.tech.thermography.domain.UserInfo;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    Optional<UserInfo> findByUser(User user);
}
