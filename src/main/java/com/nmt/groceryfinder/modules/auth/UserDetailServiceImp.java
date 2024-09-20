package com.nmt.groceryfinder.modules.auth;


import com.nmt.groceryfinder.exceptions.messages.AuthExceptionMessages;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.AccountDto;
import com.nmt.groceryfinder.modules.users.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/24/2024
 */
@Service
public class UserDetailServiceImp implements UserDetailsService {
    private final IUserService userService;

    @Autowired
    public UserDetailServiceImp( IUserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDto account = this.userService.getAccountUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        AuthExceptionMessages.USERNAME_NOT_FOUND + username
                ));

        return org.springframework.security.core.userdetails.User
                .withUsername(account.email())
                .password(account.password())
                .roles(account.role())
                .build();
    }
}