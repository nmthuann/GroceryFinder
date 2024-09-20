package com.nmt.groceryfinder.shared.passport.impl;


import com.nmt.groceryfinder.shared.passport.PassportStrategy;
import com.nmt.groceryfinder.shared.passport.common.PassportStrategyEnum;
import com.nmt.groceryfinder.shared.passport.strategies.LocalStrategy;
import com.nmt.groceryfinder.shared.passport.strategies.OauthGoogleStrategy;
import com.nmt.groceryfinder.shared.passport.strategies.OtpPhoneStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/14/2024
 */
@Component
public class PassportStrategyFactory {
    private final Map<Integer, PassportStrategy> strategies;
    @Autowired
    public PassportStrategyFactory(
            LocalStrategy localStrategy,
            OauthGoogleStrategy oauthGoogleStrategy,
            OtpPhoneStrategy otpPhoneStrategy
    ) {
       this.strategies = new HashMap<>();
       this.strategies.put(PassportStrategyEnum.LOCAL_STRATEGY.getAuthMethodId(), localStrategy);
        this.strategies.put(PassportStrategyEnum.OAUTH_STRATEGY.getAuthMethodId(), oauthGoogleStrategy);
        this.strategies.put(PassportStrategyEnum.OTP_PHONE_STRATEGY.getAuthMethodId(), otpPhoneStrategy);
    }

    public PassportStrategy getPassportStrategy(int authMethodId) {
        return strategies.get(authMethodId);
    }
}
