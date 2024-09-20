package com.nmt.groceryfinder.modules.products.controllers;

import com.nmt.groceryfinder.modules.products.domain.model.dtos.SupplierDto;
import com.nmt.groceryfinder.modules.products.services.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/30/2024
 */
@RestController
@RequestMapping("/v1/suppliers")
public class SupplierController {
    private final ISupplierService supplierService;

    @Autowired
    public SupplierController(
            ISupplierService supplierService
    ){
        this.supplierService = supplierService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<SupplierDto>> getOneById(@PathVariable Integer id) {
        Optional<SupplierDto> supplier = this.supplierService.getOneById(id);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }
}
