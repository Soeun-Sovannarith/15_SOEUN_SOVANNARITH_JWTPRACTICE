package com.rith.jwtsecurity.services.Impl;

import com.rith.jwtsecurity.Model.request.AppUserRequest;
import com.rith.jwtsecurity.Repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.rith.jwtsecurity.services.AppUserService;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return appUserRepository.getUserByEmail(email);
    }

    @Override
    public @Nullable UserDetails register(AppUserRequest request) {
        return appUserRepository.register(request);
    }
}

