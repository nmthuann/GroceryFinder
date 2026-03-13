package com.nmt.groceryfinder.modules.auth;

import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.auth.responses.AuthenticationResponseDto;
import com.nmt.groceryfinder.modules.auth.responses.LoginResponseDto;
import com.nmt.groceryfinder.modules.auth.responses.RegisterAdminResponseDto;
import com.nmt.groceryfinder.modules.auth.responses.RegisterResponseDto;
import com.nmt.groceryfinder.modules.auth.requests.*;
import jakarta.mail.MessagingException;
import jakarta.security.auth.message.AuthException;


public interface IAuthService {
    LoginResponseDto login (LoginRequestDto data) throws AuthException;
    RegisterResponseDto register(RegisterRequestDto data)throws AuthException;
    RegisterAdminResponseDto registerAdmin (
            RegisterAdminRequestDto data
    )throws AuthException, ModuleException, MessagingException;
    AuthenticationResponseDto  verifyEmail(VerifyEmailRequestDto data) throws ModuleException;
    AuthenticationResponseDto checkOtp(CheckOtpRequestDto data) throws ModuleException;
    AuthenticationResponseDto resendOtp(ResendOtpRequestDto data) throws MessagingException;
    AuthenticationResponseDto resetPassword(String email);
    AuthenticationResponseDto changePassword(String email);
    AuthenticationResponseDto forgetPassword(String email);
    AuthenticationResponseDto logout(String email);
}
