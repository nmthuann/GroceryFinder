package com.nmt.groceryfinder.common.enums;

import lombok.Getter;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/4/2024
 */
@Getter
public enum SubjectMailEnum {
    REGISTER_ADMIN_SUBJECT("MẬT KHẨU CỦA BẠN"),
    VERIFY_EMAIL_SUBJECT("XÁC NHẬN EMAIL THÀNH CÔNG"),
    RESET_PASSWORD_SUBJECT("RESTSET PASSWORD"),
    RESEND_OTP_SUBJECT("GỬI LẠI MÃ OTP"),
    NOTIFICATION_CLEAN_LOG_FILE("THÔNG BÁO DỌN DẸP CÁC FILE LOG ĐỊNH KỲ")
    ;

    private final String subject;

    SubjectMailEnum(String subject){
        this.subject = subject;
    }
}
