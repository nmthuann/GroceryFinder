package com.nmt.groceryfinder.modules.products.domain.model.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/13/2024
 */
public record UpdateSupplierDto(
        @NotBlank(message = "Supplier name is required")
        @Size(max = 100, message = "Supplier name must be less than 100 characters")
        String supplierName,

        @Size(max = 100, message = "Address must be less than 100 characters")
        String address,

        @Size(max = 15, message = "Phone number must be less than 15 characters")
        @Pattern(regexp = "^\\+?[0-9. ()-]{7,15}$", message = "Phone number is invalid")
        String phoneNumber,

        @Email(message = "Email is invalid")
        @Size(max = 100, message = "Email must be less than 100 characters")
        String email,

        @Size(max = 100, message = "Contact person must be less than 100 characters")
        String contactPerson,

        @Size(max = 100, message = "Description must be less than 100 characters")
        String description
) {
}
