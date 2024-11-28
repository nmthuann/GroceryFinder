package com.nmt.groceryfinder.modules.users.user;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.common.enums.AuthMethodEnum;
import com.nmt.groceryfinder.common.enums.RoleEnum;
import com.nmt.groceryfinder.modules.auth.dtos.requests.CreateUserDto;
import com.nmt.groceryfinder.modules.users.user.dtos.AccountDto;
import com.nmt.groceryfinder.modules.users.user.dtos.ProfileDto;
import com.nmt.groceryfinder.modules.users.user.dtos.UserDto;
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
        return findUserByAccount.map(this.userMapper::toAccountWithPasswordDto);
    }

    @Override
    public Optional<ProfileDto> getProfileByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .map(user -> new ProfileDto(
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getAvatarURL(),
                        user.getGender(),
                        user.getBirthday(),
                        user.getAddress(),
                        user.getPhone()
                ));
    }



    @Override
    public UserDto createOne(CreateUserDto data) {
        RoleEnum role = this.getRoleById(data.role());
        AuthMethodEnum authMethod = this.getAuthMethodById(data.authMethod());
        UserEntity userEntity = this.userMapper.generateEntity(data, role.name(), authMethod.name());
        return this.userMapper.toDto(
                this.userRepository.save(userEntity)
        );
    }


    private RoleEnum getRoleById(Integer roleId) {
        return switch (roleId) {
            case 1 -> RoleEnum.ADMIN;
            case 2 -> RoleEnum.USER;
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
