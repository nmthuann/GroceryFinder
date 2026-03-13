package com.nmt.groceryfinder.modules.users.services;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.modules.auth.requests.CreateUserDto;
import com.nmt.groceryfinder.modules.users.dto.AccountDto;
import com.nmt.groceryfinder.modules.users.dto.ProfileDto;
import com.nmt.groceryfinder.modules.users.dto.UserDto;

import java.util.Optional;
import java.util.UUID;

public interface IUserService extends IBaseService<UUID, UserDto> {
    Optional<AccountDto> getAccountUserByEmail(String email);
    Optional<ProfileDto> getProfileByEmail(String email);
    UserDto createOne(CreateUserDto data);
}
