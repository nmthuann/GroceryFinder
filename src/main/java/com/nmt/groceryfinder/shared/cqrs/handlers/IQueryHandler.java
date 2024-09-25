package com.nmt.groceryfinder.shared.cqrs.handlers;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/25/2024
 */
public interface IQueryHandler<T> {
    void execute(T query);
}
