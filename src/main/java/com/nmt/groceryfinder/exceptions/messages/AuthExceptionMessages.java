package com.nmt.groceryfinder.exceptions.messages;

import lombok.Getter;

@Getter
public enum AuthExceptionMessages {
    PASSWORD_WRONG("Bạn nhập sai mật khẩu."),
    LOGIN_INVALID("Email hoặc Password của bạn không hợp lệ."),
    LOGIN_FAILED("Đăng nhập thất bại"),
    EMAIL_EXIST("Email đã tồn tại."),
    VERIFY_MAIL_FAILED("Xác thực email thất bại."),
    SEND_MAIL_FAILED("Gửi mail thất bại."),
    REGISTER_USER_FAILED("Đăng ký thất bại."),
    USERNAME_NOT_FOUND("User not found with username: "),
    PASSWORD_NOT_EMPTY("Password is required"),
    EMAIL_INVALID("Invalid email format"),
    PASSWORD_TOO_SHORT("Password must be at least 8 characters long"),
    AUTH_MISSING_INFORMATION("Missing authorization information."),
    AUTH_ERROR("An error occurred while processing authentication."),
    AUTH_MISSING_API_VERSION("Missing api version information."),
    AUTH_INVALID_API_VERSION("Api version information Invalid."),
    OTP_INVALID("Invalid or expired OTP."),
    AUTH_UNKNOWN_AUTH_METHOD("Unable to determine login method with auth method id: ")
    ;


    private final String message;

    AuthExceptionMessages(String message){
        this.message = message;
    }

}


