package com.nmt.groceryfinder.modules.users.domain.model.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CustomerDto implements Serializable {
    private Long id;

    @NotNull(message = "Average Order Value (AOV) is required")
    @Min(value = 0, message = "AOV must be a positive value")
    private Integer aov;

    @NotNull(message = "Frequency is required")
    @Min(value = 0, message = "Frequency must be a positive value")
    private Integer frequency;

    @NotNull(message = "Recency is required")
    @Min(value = 0, message = "Recency must be a positive value")
    private Integer recency;

    @NotNull(message = "Customer Lifetime Value (CLV) is required")
    @Min(value = 0, message = "CLV must be a positive value")
    private Integer clv;

    @NotNull(message = "Engagement is required")
    @Min(value = 0, message = "Engagement must be a positive value")
    private Integer engagement;

    @NotNull(message = "Feedback is required")
    @Min(value = 0, message = "Feedback must be a positive value")
    private Integer feedback;

    @NotNull(message = "Total Score is required")
    @Min(value = 0, message = "Total Score must be a positive value")
    private Integer totalScore;

    @NotNull(message = "Classification is required")
    @NotEmpty(message = "Classification cannot be empty")
    private String classification;
}
