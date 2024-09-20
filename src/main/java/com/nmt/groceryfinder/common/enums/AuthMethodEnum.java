package com.nmt.groceryfinder.common.enums;

import lombok.Getter;

@Getter
public enum AuthMethodEnum {
    LOCAL_AUTHENTICATION(1),
    GOOGLE(2),
    FACEBOOK(3);
    private final int authMethodId;
    AuthMethodEnum(int authMethodId){
        this.authMethodId = authMethodId;
    }

}
