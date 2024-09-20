package com.nmt.groceryfinder.modules.users.services.impl;


import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.common.enums.AuthMethodEnum;
import com.nmt.groceryfinder.common.enums.RoleEnum;
import com.nmt.groceryfinder.modules.auth.dtos.requests.CreateUserDto;
import com.nmt.groceryfinder.modules.users.domain.mappers.UserMapper;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.AccountDto;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.UserDto;
import com.nmt.groceryfinder.modules.users.domain.model.entities.UserEntity;
import com.nmt.groceryfinder.modules.users.repositories.IUserRepository;
import com.nmt.groceryfinder.modules.users.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService
        extends AbstractBaseService<UserEntity, UUID, UserDto>
        implements IUserService
{

    private final IUserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(
            IUserRepository userRepository,
            UserMapper userMapper
    ) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<AccountDto> getAccountUserByEmail(String email) {
        Optional<UserEntity> findUserByAccount = this.userRepository.findByEmail(email);
        if(findUserByAccount!=null){
            return Optional.ofNullable(this.userMapper.toAccountWithPasswordDto(
                    findUserByAccount.get())
            );
        }
        return null;
    }

    @Override
    public Optional<UserDto> getCustomerByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .map(this.userMapper::toDto);
    }

    @Override
    public Optional<UserDto> getCustomerByPhone(String phone) {
        return this.userRepository.findByPhone(phone)
                .map(this.userMapper::toDto);
    }

    @Override
    public UserDto createOne(CreateUserDto data) {
        UserEntity newUser = new UserEntity();
        newUser.setEmail(data.email());
        newUser.setPassword(data.hashedPassword());
        newUser.setStatus(true);
        newUser.setFirstName(data.firstName());
        newUser.setLastName(data.lastName());
        newUser.setBirthday(data.birthday());
        newUser.setGender(data.gender());
        newUser.setPhone(data.phone());
        newUser.setAddress(data.address());
        newUser.setAvatarURL(data.avatarUrl());
        RoleEnum role = getRoleById(data.role());
        newUser.setRole(role.name());
        AuthMethodEnum authMethod = getAuthMethodById(data.authMethod());
        newUser.setAuthMethod(authMethod.name());
        return this.userMapper.toDto(this.userRepository.save(newUser));
    }


    private RoleEnum getRoleById(Integer roleId) {
        return switch (roleId) {
            case 1 -> RoleEnum.ADMIN;
            case 2 -> RoleEnum.CUSTOMER;
            case 3 -> RoleEnum.MANAGER;
            default -> throw new IllegalArgumentException("Invalid roleId: " + roleId);
        };
    }

    private AuthMethodEnum getAuthMethodById(Integer authMethodId) {
        return switch (authMethodId) {
            case 1 -> AuthMethodEnum.LOCAL_AUTHENTICATION;
            case 2 -> AuthMethodEnum.GOOGLE;
            case 3 -> AuthMethodEnum.FACEBOOK;
            default -> throw new IllegalArgumentException("Invalid authMethodId: " + authMethodId);
        };
    }
}
