package com.nmt.groceryfinder.modules.inventories.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "warehouses",  indexes = {@Index(name = "idx_address", columnList = "address", unique = true)})
@Getter
@Setter
public class WarehouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "warehouse_name", nullable = false, length = 50)
    private String warehouseName;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "status", nullable = false)
    private boolean status;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InventoryEntity> inventories;
}
