package com.nmt.groceryfinder.modules.products.services.impl;

import com.nmt.groceryfinder.common.bases.AbstractBaseService;
import com.nmt.groceryfinder.exceptions.ModuleException;
import com.nmt.groceryfinder.exceptions.messages.ProductsModuleExceptionMessages;
import com.nmt.groceryfinder.modules.products.domain.mappers.ReviewMapper;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.ReviewDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.CreateReviewDto;
import com.nmt.groceryfinder.modules.products.domain.model.dtos.requests.UpdateReviewDto;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ProductEntity;
import com.nmt.groceryfinder.modules.products.domain.model.entities.ReviewEntity;
import com.nmt.groceryfinder.modules.products.repositories.IReviewRepository;
import com.nmt.groceryfinder.modules.products.services.IReviewService;
import com.nmt.groceryfinder.modules.products.services.ISpuSkuMappingService;
import com.nmt.groceryfinder.modules.users.domain.mappers.UserMapper;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.AccountDto;
import com.nmt.groceryfinder.modules.users.domain.model.dtos.UserDto;
import com.nmt.groceryfinder.modules.users.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService
        extends AbstractBaseService<ReviewEntity, Integer, ReviewDto>
        implements IReviewService
{
    private final IReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final IUserService userService;
    private final UserMapper userMapper;
    private final ISpuSkuMappingService spuSkuMappingService;


    @Autowired
    public ReviewService(
            IReviewRepository reviewRepository,
            ReviewMapper reviewMapper,
            IUserService userService,
            UserMapper userMapper,
            ISpuSkuMappingService spuSkuMappingService


    ) {
        super(reviewRepository, reviewMapper);
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.spuSkuMappingService = spuSkuMappingService;
    }


    @Override
    public ReviewDto createOne(ProductEntity productCreated, String email, CreateReviewDto data) {
        Optional<AccountDto> findUser = this.userService.getAccountUserByEmail(email);
        Optional<UserDto> findById = this.userService.getOneById(findUser.get().id());
        ReviewEntity createReview = new ReviewEntity();
        createReview.setRating(data.rating());
        createReview.setName(data.name());
        createReview.setLocation(data.location());
        createReview.setFeedback(data.feedback());
        createReview.setImageUrl(data.imageUrl());
        createReview.setLink(data.link());
        createReview.setClassification(data.classification());
        createReview.setProduct(productCreated);
        createReview.setUser(this.userMapper.toEntity(findById.get()));
        ReviewEntity reviewCreated = this.reviewRepository.save(createReview);
        return this.reviewMapper.toDto(reviewCreated);
    }

    @Override
    public ReviewDto updateOneById(Integer id, UpdateReviewDto data) throws ModuleException {
        Optional<ReviewEntity> findReview = this.reviewRepository.findById(id);
        if (!findReview.isPresent()) {
            throw new ModuleException(ProductsModuleExceptionMessages.REVIEW_NOT_FOUND.getMessage());
        }
        ReviewEntity updateReview = findReview.get();
        if (data.feedback() != null) {
            updateReview.setFeedback(data.feedback());
        }
        if (data.rating() != null) {
            updateReview.setRating(data.rating());
        }
        if (data.imageUrl() != null) {
            updateReview.setImageUrl(data.imageUrl());
        }

        return this.reviewMapper.toDto(this.reviewRepository.save(updateReview));
    }

    @Override
    public Page<ReviewDto> getReviewsByProduct(ProductEntity productCreated, Pageable pageable) {
        Page<ReviewEntity> findReviews = this.reviewRepository.findByProductOrderByCreatedAt(productCreated, pageable);
        return findReviews.map(review -> this.reviewMapper.toDto(review));
    }
}
