package com.nmt.groceryfinder.modules.users.domain.mappers;

import com.nmt.groceryfinder.common.bases.AbstractModelMapper;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.AccountDto;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.UserDto;
import com.nmt.groceryfinder.modules.users.domain.model.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper extends AbstractModelMapper<UserEntity, UUID, UserDto> {

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        super(modelMapper, UserEntity.class, UserDto.class);
    }
    public AccountDto toAccountWithPasswordDto(UserEntity entity){
        return new AccountDto(
                entity.getId(),
                entity.getEmail(),
                entity.isStatus(),
                entity.getSub(),
                entity.getPassword(),
                entity.getRefreshToken(),
                entity.getAuthMethod(),
                entity.getRole()
        );
    }
}
