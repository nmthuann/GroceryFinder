package com.nmt.groceryfinder.exceptions.messages;

import lombok.Getter;

@Getter
public enum UsersModuleExceptionMessages {
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND."),
    CREATE_USER_PAYMENT_FAIL("CREATE_USER_PAYMENT_FAIL."),
    USER_NOT_FOUND_OR_EMAIL_INVALID("Not Found Email or Email Invalid"),
    ACCOUNT_NOT_FOUND("Account not found with ID: "),
    POSITION_NOT_FOUND("POSITION_NOT_FOUND"),
    USER_NOT_FOUND("USER_NOT_FOUND")
    ;


    private final String message;

    UsersModuleExceptionMessages(String message){
        this.message = message;
    }
}
