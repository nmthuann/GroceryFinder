package com.nmt.groceryfinder.shared.passport.strategies;

import com.nmt.groceryfinder.common.dtos.Payload;
import com.nmt.groceryfinder.modules.auth.dtos.requests.PassportDto;
import com.nmt.groceryfinder.shared.passport.PassportStrategy;
import jakarta.security.auth.message.AuthException;
import org.springframework.stereotype.Component;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/13/2024
 */
@Component
public class OtpPhoneStrategy  implements PassportStrategy {
    @Override
    public Payload validate(PassportDto data) throws AuthException {
        return null;
    }
}
