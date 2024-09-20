package com.nmt.groceryfinder.modules.auth.dtos.requests;

import lombok.Getter;
import lombok.Setter;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/6/2024
 */
@Getter
@Setter
public class GetUserInformationDto{
    String firstName;
    String lastName;
    String address;
    String avatarUrl;

}
