package com.nmt.groceryfinder.modules.users.domain.model.entities;

import com.nmt.groceryfinder.common.bases.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "employees", indexes = {
        @Index(name = "idx_employee_cccd", columnList = "cccd")
})
public class EmployeeEntity extends AuditableEntity {
    @Column(name = "cccd", length = 50, unique = true, nullable = false)
    private String cccd;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "work_status", nullable = false)
    private Boolean workStatus;

    @Column(nullable = false)
    private String position;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name = "user_id",
            unique = true,
            foreignKey = @ForeignKey(name = "fk_employee_user")
    )
    private UserEntity user;
}
