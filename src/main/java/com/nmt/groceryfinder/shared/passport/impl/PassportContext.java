package com.nmt.groceryfinder.shared.passport.impl;


import com.nmt.groceryfinder.common.dtos.Payload;
import com.nmt.groceryfinder.exceptions.messages.AuthExceptionMessages;
import com.nmt.groceryfinder.modules.auth.dtos.requests.PassportDto;
import com.nmt.groceryfinder.shared.passport.PassportStrategy;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/13/2024
 */
@Component
public class PassportContext {
    private final PassportStrategyFactory passportStrategyFactory;
    @Autowired
    public PassportContext(PassportStrategyFactory passportStrategyFactory) {
        this.passportStrategyFactory = passportStrategyFactory;
    }

    public Payload executeStrategy(int authMethodId, PassportDto data) throws AuthException {
        PassportStrategy strategy = passportStrategyFactory.getPassportStrategy(authMethodId);
        if (strategy == null) {
            throw new AuthException(
                    AuthExceptionMessages.AUTH_UNKNOWN_AUTH_METHOD.getMessage() + authMethodId);
        }
        return strategy.validate(data);
    }
}
