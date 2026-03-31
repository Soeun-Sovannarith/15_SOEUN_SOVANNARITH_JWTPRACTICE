package com.rith.jwtsecurity.Controllers;

import com.rith.jwtsecurity.Model.Response.AuthResponse;
import com.rith.jwtsecurity.Model.request.AppUserRequest;
import com.rith.jwtsecurity.Model.request.AuthRequest;
import com.rith.jwtsecurity.jwt.JwtService;
import com.rith.jwtsecurity.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auths")
@RequiredArgsConstructor
public class AuthController {
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) throws Exception {
        authenticate(request.getEmail(), request.getPassword());
        final UserDetails userDetails = appUserService.loadUserByUsername(request.getEmail());
        final String token = jwtService.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AppUserRequest request){
        if (request.getPassword() != null) {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        UserDetails userDetails = appUserService.register(request);
        if (userDetails == null) {
            return ResponseEntity.badRequest().body("Registration failed");
        }
        final String token = jwtService.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }
}