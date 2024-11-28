package com.nmt.groceryfinder.modules.users.customer;

import com.nmt.groceryfinder.modules.users.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer aov = 0; // Average Order Value - AOV

    @Column(nullable = false)
    private Integer frequency;

    @Column(nullable = false)
    private Integer recency = 0; //Thời gian từ lần mua cuối cùng (Recency)

    @Column(nullable = false)
    private Integer clv = 0; //Customer Lifetime Value - CLV

    @Column(nullable = false)
    private Integer engagement = 0; // Mức độ tương tác (Engagement Level):

    @Column(nullable = false)
    private Integer feedback = 0;

    @Column(nullable = false)
    private Integer totalScore = 0;

    @Column(nullable = false)
    private String classification;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_customer_user"))
    private UserEntity user;
}
