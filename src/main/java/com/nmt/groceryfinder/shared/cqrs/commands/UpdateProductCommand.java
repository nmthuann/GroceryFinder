package com.nmt.groceryfinder.shared.cqrs.commands;

import java.util.UUID;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/25/2024
 */
public record UpdateProductCommand(
        UUID id,
        String productName,
        String slug,
        String description
) {
}
