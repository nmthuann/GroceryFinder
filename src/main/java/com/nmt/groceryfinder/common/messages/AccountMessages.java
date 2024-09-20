package com.nmt.groceryfinder.common.messages;

import lombok.Getter;

@Getter
public enum AccountMessages {
    EMAIL_INVALID("Email should be valid"),
    EMAIL_NOT_BLANK("Email is required"),
    EMAIL_SIZE("CREATE_USER_PAYMENT_FAIL."),
    USER_NOT_FOUND_OR_EMAIL_INVALID("Not Found Email or Email Invalid")
    ;


    private final String message;

    AccountMessages(String message){
        this.message = message;
    }
}
