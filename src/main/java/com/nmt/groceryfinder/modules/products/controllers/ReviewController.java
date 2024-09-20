package com.nmt.groceryfinder.modules.products.controllers;

import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ReviewDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.UpdateReviewDto;
import com.nmt.groceryfinder.modules.products.services.IReviewService;
import com.nmt.groceryfinder.shared.logging.LoggingInterceptor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/4/2024
 */
@RestController
@RequestMapping("/v1/reviews")
@Tag(name = "Reviews")
public class ReviewController {
    private final IReviewService reviewService;

    @Autowired
    public ReviewController (IReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PatchMapping("/{id}")
    @LoggingInterceptor
    public ResponseEntity<ReviewDto> updateOneById(
            @PathVariable Integer id,
            @RequestBody UpdateReviewDto data
    ) throws ModuleException {
        ReviewDto reviewUpdated = this.reviewService.updateOneById(id, data);
        return new ResponseEntity<>(reviewUpdated, HttpStatus.OK);
    }
}