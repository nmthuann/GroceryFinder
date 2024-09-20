package com.nmt.groceryfinder.shared.passport.strategies;


import com.nmt.groceryfinder.common.dtos.Payload;
import com.nmt.groceryfinder.exceptions.messages.AuthExceptionMessages;
import com.nmt.groceryfinder.modules.auth.dtos.requests.PassportDto;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.AccountDto;
import com.nmt.groceryfinder.modules.users.services.IUserService;
import com.nmt.groceryfinder.shared.passport.PassportStrategy;
import com.nmt.groceryfinder.shared.passport.common.UsernamePasswordLoginDto;
import com.nmt.groceryfinder.utils.PasswordUtil;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/13/2024
 */
@Component
public class LocalStrategy implements PassportStrategy {
    private final IUserService userService;
    private final PasswordUtil passwordUtil;


    @Autowired
    public LocalStrategy(IUserService userService, PasswordUtil passwordUtil) {
        this.userService = userService;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public Payload validate(PassportDto data) throws AuthException {
        UsernamePasswordLoginDto convertData = new UsernamePasswordLoginDto(data.parameterA(), data.parameterB());

        AccountDto account = this.userService.getAccountUserByEmail(convertData.email())
                .filter(AccountDto::status)
                .orElseThrow(() -> new AuthException(AuthExceptionMessages.LOGIN_INVALID.getMessage()));

        if (!this.passwordUtil.comparePassword(convertData.password(), account.password())) {
            throw new AuthException(AuthExceptionMessages.PASSWORD_WRONG.getMessage());
        }

        return new Payload(
                account.id(),
                account.email(),
                account.role()
        );
    }
}
