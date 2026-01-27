package com.tech.thermography.repository;

import com.tech.thermography.domain.User;
import com.tech.thermography.domain.UserInfo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    Optional<UserInfo> findByUser(User user);

    @Query("SELECT ui FROM UserInfo ui LEFT JOIN FETCH ui.user")
    List<UserInfo> findAllWithUser();

    @Query("SELECT ui FROM UserInfo ui LEFT JOIN FETCH ui.user WHERE ui.id = :id")
    Optional<UserInfo> findByIdWithUser(UUID id);
}
