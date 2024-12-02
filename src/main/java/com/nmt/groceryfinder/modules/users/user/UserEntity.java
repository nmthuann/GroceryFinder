package com.nmt.groceryfinder.modules.users.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.nmt.groceryfinder.common.bases.AuditableEntity;
import com.nmt.groceryfinder.modules.users.customer.CustomerEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends AuditableEntity {
    @Column(name = "email", length = 50, unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "sub", nullable = false)
    private String sub;

    @Column(name = "refresh_token", nullable = true)
    private String refreshToken;

    @Column(name = "first_name", length = 50, nullable = false)
    @Nationalized
    private String firstName;

    @Column(name = "last_name", nullable = true)
    @Nationalized
    private String lastName;

    @Column(name = "avatar_url")
    private String avatarURL;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", length = 10, nullable = false, unique = true)
    private String phone;

    @Column(name = "auth_method", length = 50, nullable = false)
    private String authMethod;

    @Column(name = "role", length = 20, nullable = false)
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private CustomerEntity customer;
}