package com.nmt.groceryfinder.modules.products.domain.model.dtos.responses;

import java.util.List;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/1/2024
 */
public record ProductResponse(
        String product,
        List<String> productSkus
) {
}
