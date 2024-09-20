package com.nmt.groceryfinder.modules.users.services;


import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.modules.auth.dtos.requests.CreateUserDto;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.AccountDto;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.UserDto;

import java.util.Optional;
import java.util.UUID;

public interface IUserService extends IBaseService<UUID, UserDto> {
    Optional<AccountDto> getAccountUserByEmail(String email);
    Optional<UserDto> getCustomerByEmail(String email);
    Optional<UserDto> getCustomerByPhone(String phone);
    UserDto createOne(CreateUserDto data);
}
