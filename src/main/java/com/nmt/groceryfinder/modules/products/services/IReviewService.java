package com.nmt.groceryfinder.modules.products.services;


import com.nmt.groceryfinder.common.bases.IBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ReviewDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateReviewDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.UpdateReviewDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReviewService extends IBaseService<Integer, ReviewDto> {

    ReviewDto createOne(ProductEntity productCreated, String email, CreateReviewDto data);
    ReviewDto updateOneById(Integer id, UpdateReviewDto data) throws ModuleException;
    Page<ReviewDto> getReviewsByProduct(ProductEntity productCreated, Pageable pageable);

}
