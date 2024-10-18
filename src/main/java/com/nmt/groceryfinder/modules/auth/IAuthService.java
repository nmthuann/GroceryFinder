package com.nmt.groceryfinder.modules.auth;

import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.auth.dtos.requests.*;
import com.nmt.groceryfinder.modules.auth.dtos.responses.AuthenticationResponseDto;
import com.nmt.groceryfinder.modules.auth.dtos.responses.LoginResponseDto;
import com.nmt.groceryfinder.modules.auth.dtos.responses.RegisterAdminResponseDto;
import com.nmt.groceryfinder.modules.auth.dtos.responses.RegisterResponseDto;
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
