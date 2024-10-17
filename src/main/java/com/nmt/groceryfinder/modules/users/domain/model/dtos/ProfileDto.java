package com.nmt.groceryfinder.modules.users.domain.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/17/2024
 */
public record ProfileDto(
        @NotEmpty(message = "Email is required")
        @Email(message = "Email should be valid")
        @Size(max = 50, message = "Email should not be longer than 50 characters")
        String email,
        @NotEmpty(message = "First name is required")
        @Size(max = 50, message = "First name must be less than or equal to 50 characters")
        String firstName,
        @Size(max = 50, message = "Last name must be less than or equal to 50 characters")
        String lastName,
        @URL(message = "Avatar URL must be a valid URL")
        String avatarURL,
        @Size(max = 20, message = "Gender must be less than or equal to 20 characters")
        String gender,
        @NotEmpty(message = "Birthday is required")
        Date birthday,
        @Size(max = 255, message = "Address must be less than or equal to 255 characters")
        String address,
        @NotEmpty(message = "Phone number is required")
        @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
        String phone
) {
}
