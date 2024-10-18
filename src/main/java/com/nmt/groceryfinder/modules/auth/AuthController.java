package com.nmt.groceryfinder.modules.auth;

import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.exceptions.messages.AuthExceptionMessages;
import com.nmt.groceryfinder.modules.auth.dtos.requests.*;
import com.nmt.groceryfinder.modules.auth.dtos.responses.AuthenticationResponseDto;
import com.nmt.groceryfinder.modules.auth.dtos.responses.LoginResponseDto;
import com.nmt.groceryfinder.modules.auth.dtos.responses.RegisterAdminResponseDto;
import com.nmt.groceryfinder.modules.auth.dtos.responses.RegisterResponseDto;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/13/2024
 */
@RestController
@RequestMapping("/v1/auth")
@Tag(name = "AUTH")
public class AuthController {
    private final IAuthService authService;


    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;

    }

    @LoggingInterceptor
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto data)  {
        try{
            LoginResponseDto response = authService.login(data);
            return ResponseEntity.ok(response);
        }catch (AuthException e){
            if(e.getMessage().equals(AuthExceptionMessages.PASSWORD_WRONG.getMessage())){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthenticationResponseDto(false, e.getMessage()));
            }
            else if(e.getMessage().equals(AuthExceptionMessages.LOGIN_INVALID.getMessage())){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthenticationResponseDto(false, e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponseDto(
                            false,
                            AuthExceptionMessages.LOGIN_FAILED.getMessage()
                    ));
        }
    }

    @LoggingInterceptor
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto data)  {
        try {
            RegisterResponseDto response = authService.register(data);
            return ResponseEntity.ok(response);
        }  catch (AuthException e) {
            if (e.getMessage().equals(AuthExceptionMessages.EMAIL_EXIST.getMessage())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthenticationResponseDto(false, e.getMessage()));
            }
            if(e.getMessage().equals(AuthExceptionMessages.REGISTER_USER_FAILED.getMessage())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthenticationResponseDto(false, e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponseDto(false, e.getMessage()));
        }
    }


    @PostMapping("/verify-email")
    @LoggingInterceptor
    public ResponseEntity<AuthenticationResponseDto> verifyEmail(@RequestBody VerifyEmailRequestDto data) {
        try {
            AuthenticationResponseDto response = authService.verifyEmail(data);
            return ResponseEntity.ok(response);
        } catch (ModuleException e) {
            if (e.getMessage().equals(AuthExceptionMessages.EMAIL_EXIST.getMessage())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthenticationResponseDto(false, e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponseDto(false, e.getMessage()));
        } catch (RuntimeException e) {
            if (e.getMessage().equals(AuthExceptionMessages.VERIFY_MAIL_FAILED.getMessage())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthenticationResponseDto(false, e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponseDto(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponseDto(false, "Unexpected error: " + e.getMessage()));
        }
    }


    @PostMapping("/check-otp")
    @LoggingInterceptor
    public ResponseEntity<AuthenticationResponseDto> checkOtp(@RequestBody CheckOtpRequestDto data) {
        try {
            AuthenticationResponseDto response = authService.checkOtp(data);
            return ResponseEntity.ok(response);
        } catch (ModuleException e) {
            if (e.getMessage().equals(AuthExceptionMessages.OTP_INVALID.getMessage())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthenticationResponseDto(false, e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponseDto(false, e.getMessage()));
        }
    }

    @PostMapping("/resend-otp")
    @LoggingInterceptor
    public ResponseEntity<AuthenticationResponseDto> resendOtp(@RequestBody ResendOtpRequestDto data) {
        try {
            AuthenticationResponseDto response = authService.resendOtp(data);
            return ResponseEntity.ok(response);
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponseDto(false, e.getMessage()));
        }
    }

    @LoggingInterceptor
    @PostMapping("/reset-password")
    public ResponseEntity<AuthenticationResponseDto> resetPassword(@RequestParam String email) {
        AuthenticationResponseDto response = authService.resetPassword(email);
        return ResponseEntity.ok(response);
    }

    @LoggingInterceptor
    @PostMapping("/change-password")
    public ResponseEntity<AuthenticationResponseDto> changePassword(@RequestParam String email) {
        AuthenticationResponseDto response = authService.changePassword(email);
        return ResponseEntity.ok(response);
    }

    @LoggingInterceptor
    @PostMapping("/forget-password")
    public ResponseEntity<AuthenticationResponseDto> forgetPassword(@RequestParam String email) {
        AuthenticationResponseDto response = authService.forgetPassword(email);
        return ResponseEntity.ok(response);
    }

    @LoggingInterceptor
    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponseDto> logout(@RequestParam String email) {
        AuthenticationResponseDto response = authService.logout(email);
        return ResponseEntity.ok(response);
    }


    @LoggingInterceptor
    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(
            @RequestBody RegisterAdminRequestDto data
    ) throws MessagingException, AuthException, ModuleException {
        RegisterAdminResponseDto response = authService.registerAdmin(data);
        return ResponseEntity.ok(response);
    }
}
