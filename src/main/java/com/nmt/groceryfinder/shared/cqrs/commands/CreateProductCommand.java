package com.nmt.groceryfinder.shared.cqrs.commands;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/25/2024
 */
public record CreateProductCommand(
        String productName,
        String slug,
        String description
) {
}
