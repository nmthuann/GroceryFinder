package com.nmt.groceryfinder.modules.users.domain.model.dtos;

import com.nmt.groceryfinder.common.bases.AbstractBaseDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EmployeeDto extends AbstractBaseDto implements Serializable {
    @NotNull(message = "CCCD is required")
    @Size(max = 50, message = "CCCD must be less than or equal to 50 characters")
    private String cccd;
    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "Salary must be a positive value")
    private Integer salary;
    @NotNull(message = "Work status is required")
    private Boolean workStatus;
    private String position;
}
