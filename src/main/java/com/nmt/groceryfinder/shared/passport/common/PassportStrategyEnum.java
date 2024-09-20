package com.nmt.groceryfinder.shared.passport.common;

import lombok.Getter;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/13/2024
 */
@Getter
public enum PassportStrategyEnum {
    LOCAL_STRATEGY(1),
    OAUTH_STRATEGY(2),
    OTP_PHONE_STRATEGY(3);
    private final int authMethodId;
    PassportStrategyEnum(int authMethodId){
        this.authMethodId = authMethodId;
    }
}
