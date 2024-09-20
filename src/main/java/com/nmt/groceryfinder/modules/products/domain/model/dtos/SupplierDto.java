package com.nmt.groceryfinder.modules.products.domain.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
@Getter
@Setter
public class SupplierDto implements Serializable {
    private Integer id;
    @NotBlank(message = "Supplier name is required")
    @Size(max = 100, message = "Supplier name must be less than 100 characters")
    private String supplierName;

    @Size(max = 100, message = "Address must be less than 100 characters")
    private String address;

    @Size(max = 15, message = "Phone number must be less than 15 characters")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,15}$", message = "Phone number is invalid")
    private String phoneNumber;

    @Email(message = "Email is invalid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @Size(max = 100, message = "Contact person must be less than 100 characters")
    private String contactPerson;

    @Size(max = 100, message = "Description must be less than 100 characters")
    private String description;
}
