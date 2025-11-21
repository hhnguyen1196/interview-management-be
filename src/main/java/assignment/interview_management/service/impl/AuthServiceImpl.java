package assignment.interview_management.service.impl;

import assignment.interview_management.dto.ChangePasswordRequest;
import assignment.interview_management.dto.LoginRequest;
import assignment.interview_management.dto.LoginResponse;
import assignment.interview_management.security.TokenProvider;
import assignment.interview_management.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private TokenProvider tokenProvider;

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        return LoginResponse.builder()
                .token(tokenProvider.generateToken(authentication))
                .build();
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {

    }
}
