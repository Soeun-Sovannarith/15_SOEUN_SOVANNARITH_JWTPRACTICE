package com.rith.jwtsecurity.services;

import com.rith.jwtsecurity.Model.request.AppUserRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AppUserService extends UserDetailsService {
    @Nullable UserDetails register(AppUserRequest request);
}
