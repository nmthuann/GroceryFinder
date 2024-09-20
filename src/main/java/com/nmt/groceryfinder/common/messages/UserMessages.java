package com.nmt.groceryfinder.common.messages;

import lombok.Getter;

@Getter
public enum UserMessages {
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND."),
    CREATE_USER_PAYMENT_FAIL("CREATE_USER_PAYMENT_FAIL."),
    USER_NOT_FOUND_OR_EMAIL_INVALID("Not Found Email or Email Invalid")
    ;


    private final String message;

    UserMessages(String message){
        this.message = message;
    }
}
