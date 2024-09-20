package com.nmt.groceryfinder.shared.passport;

import com.nmt.groceryfinder.common.dtos.Payload;
import com.nmt.groceryfinder.modules.auth.dtos.requests.PassportDto;
import jakarta.security.auth.message.AuthException;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/13/2024
 */
public interface PassportStrategy {
    Payload validate(PassportDto data) throws AuthException;
}
