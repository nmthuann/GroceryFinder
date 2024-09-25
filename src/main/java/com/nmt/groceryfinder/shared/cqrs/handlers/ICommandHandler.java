package com.nmt.groceryfinder.shared.cqrs.handlers;

import com.nmt.groceryfinder.shared.cqrs.commands.CreateProductCommand;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/25/2024
 */
public interface ICommandHandler<T> {
    void execute(T command);
}
