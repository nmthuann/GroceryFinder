package com.nmt.groceryfinder.modules.users.user.dtos;


import com.nmt.groceryfinder.common.bases.AbstractBaseDto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserDto extends AbstractBaseDto implements Serializable {
    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 50, message = "Email should not be longer than 50 characters")
    String email;
    @NotEmpty(message = "Status is required")
    Boolean status;
    String sub;
    @NotEmpty(message = "First name is required")
    @Size(max = 50, message = "First name must be less than or equal to 50 characters")
    private String firstName;
    @Size(max = 50, message = "Last name must be less than or equal to 50 characters")
    private String lastName;
    @URL(message = "Avatar URL must be a valid URL")
    private String avatarURL;
    @Size(max = 20, message = "Gender must be less than or equal to 20 characters")
    private String gender;
    @NotEmpty(message = "Birthday is required")
    private Date birthday;
    @Size(max = 255, message = "Address must be less than or equal to 255 characters")
    private String address;
    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;
    private String refreshToken;
    @NotEmpty
    private String authMethod;
    @NotEmpty
    private String role;
}
