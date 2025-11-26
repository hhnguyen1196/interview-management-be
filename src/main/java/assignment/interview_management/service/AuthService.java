package assignment.interview_management.service;

import assignment.interview_management.dto.ChangePasswordRequest;
import assignment.interview_management.dto.LoginRequest;
import assignment.interview_management.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void changePassword(ChangePasswordRequest request);

    void forgotPassword(String username);
}
