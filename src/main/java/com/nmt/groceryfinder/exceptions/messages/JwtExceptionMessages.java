package com.nmt.groceryfinder.exceptions.messages;

import lombok.Getter;

@Getter
public enum JwtExceptionMessages {

    EXTRACT_USERNAME_FAILED("Failed to extract email from token."),

    ;


    private final String message;

    JwtExceptionMessages(String message){
        this.message = message;
    }
}
