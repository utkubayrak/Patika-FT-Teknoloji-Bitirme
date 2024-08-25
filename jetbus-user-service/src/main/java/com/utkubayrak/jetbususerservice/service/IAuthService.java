package com.utkubayrak.jetbususerservice.service;

import com.utkubayrak.jetbususerservice.dto.request.*;
import com.utkubayrak.jetbususerservice.dto.response.LoginResponse;
import com.utkubayrak.jetbususerservice.dto.response.RegisterResponse;

public interface IAuthService {
    public RegisterResponse registerUser(RegisterRequest registerRequest) throws Exception;

    public LoginResponse loginUser(LoginRequest loginRequest);

    public void initiatePasswordReset(EmailVerificationRequest emailVerificationRequest);

    public void verifyUser(VerifyUserRequest verifyUserRequest);
    public void resendVerify(EmailVerificationRequest emailVerificationRequest);
    public void resetPassword(PasswordResetRequest passwordResetRequest);
}
