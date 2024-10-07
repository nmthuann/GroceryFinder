package com.nmt.groceryfinder.modules.products.controllers;

import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.BrandDto;
import com.nmt.groceryfinder.modules.products.services.IBrandService;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 7/20/2024
 */
@RestController
@RequestMapping("/v1/brands")
public class BrandController {

    private final IBrandService brandService;

    @Autowired
    public BrandController(
            IBrandService brandService
    ){
        this.brandService = brandService;
    }

    @GetMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<BrandDto> getOneById(@PathVariable Integer id) {
        Optional<BrandDto> brand = this.brandService.getOneById(id);
        return brand.map(brandDto -> new ResponseEntity<>(brandDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    @LoggingInterceptor
    public ResponseEntity<BrandDto> createOne(@RequestBody BrandDto data) {
        BrandDto brandCreated = this.brandService.createOne(data);
        return new ResponseEntity<>(brandCreated, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<BrandDto> updateOneById(@PathVariable Integer id, @RequestBody BrandDto data) {
        BrandDto brandUpdated = this.brandService.updateOneById(id, data);
        return new ResponseEntity<>(brandUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<Void> deleteOneById(@PathVariable Integer id) throws ModuleException {
        brandService.deleteOneById(id);
        try {
            this.brandService.getOneById(id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("")
    @LoggingInterceptor
    public ResponseEntity<?> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String brandBusiness,
            @RequestParam(required = false, defaultValue = "true") Boolean isPagination
    ) {
        try {
            if (!isPagination && brandBusiness != "") {
                Iterable<BrandDto> brands = this.brandService.getBrandsByBrandBusiness(brandBusiness);
                return new ResponseEntity<>(brands, HttpStatus.OK);
            } else {
                Pageable pageable = PageRequest.of(page, size);
                Page<BrandDto> brands = this.brandService.getAllPaginated(pageable);
                return new ResponseEntity<>(brands, HttpStatus.OK);
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }
}
