package com.rith.jwtsecurity.Model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NullMarked;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NullMarked
public class AppUserRequest {
    private String fullName;
    private String email;
    private String password;
    private List<String> roles;
}
