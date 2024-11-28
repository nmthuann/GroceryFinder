package com.nmt.groceryfinder.modules.users.user;

import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.modules.auth.dtos.requests.CreateUserDto;
import com.nmt.groceryfinder.modules.users.user.dtos.AccountDto;
import com.nmt.groceryfinder.modules.users.user.dtos.ProfileDto;
import com.nmt.groceryfinder.modules.users.user.dtos.UserDto;

import java.util.Optional;
import java.util.UUID;

public interface IUserService extends IBaseService<UUID, UserDto> {
    Optional<AccountDto> getAccountUserByEmail(String email);
    Optional<ProfileDto> getProfileByEmail(String email);
    UserDto createOne(CreateUserDto data);
}
