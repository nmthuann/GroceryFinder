package com.nmt.groceryfinder.modules.products.domain.model.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ImageDto implements Serializable {
    private UUID id;
    @NotNull(message = "Image URL is required")
    @URL(message = "Image URL must be a valid URL")
    private String imageUrl;
}
