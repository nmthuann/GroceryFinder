package com.nmt.groceryfinder.modules.products.domain.model.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BrandDto implements Serializable {
        private Integer id;

        @NotNull(message = "Name is required")
        @Size(max = 100, message = "Name must be less than or equal to 100 characters")
        private String name;

        private String description;

        @NotNull(message = "Brand Business is required")
        private String brandBusiness;
}
