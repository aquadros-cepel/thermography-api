package com.tech.thermography.service;

import com.tech.thermography.domain.User;
import com.tech.thermography.domain.UserInfo;
import com.tech.thermography.repository.UserInfoRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthenticatedUserService {

    private final UserService userService;
    private final UserInfoRepository userInfoRepository;

    public AuthenticatedUserService(UserService userService, UserInfoRepository userInfoRepository) {
        this.userService = userService;
        this.userInfoRepository = userInfoRepository;
    }

    public User getCurrentUser() {
        return userService.getUserWithAuthorities().orElseThrow(() -> new RuntimeException("Usuário não autenticado"));
    }

    public Optional<UserInfo> getCurrentUserInfo() {
        User user = getCurrentUser();
        return userInfoRepository.findByUser(user);
    }

    public UserInfo requireCurrentUserInfo() {
        return getCurrentUserInfo().orElseThrow(() -> new RuntimeException("UserInfo não encontrado para o usuário autenticado"));
    }
}
