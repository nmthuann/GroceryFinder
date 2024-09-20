package com.nmt.groceryfinder.modules.products.domain.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.UserDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class ReviewDto implements Serializable {
    private Integer id;
    @JsonIgnore
    private UserDto user;
    private String name;
    private String location; // vị trí sản phẩm
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;
    @NotNull(message = "Feedback is required")
    @NotEmpty(message = "Feedback cannot be empty")
    private String feedback;
    private String imageUrl;
    private Date createdAt;
    private String link;
    private String classification; // phan loai san pham
}
