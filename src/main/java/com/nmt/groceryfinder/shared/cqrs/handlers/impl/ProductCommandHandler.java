package com.nmt.groceryfinder.shared.cqrs.handlers.impl;

import com.nmt.groceryfinder.shared.cqrs.commands.CreateProductCommand;
import com.nmt.groceryfinder.shared.cqrs.handlers.ICommandHandler;
import com.nmt.groceryfinder.shared.elasticsearch.IElasticsearchService;
import com.nmt.groceryfinder.shared.elasticsearch.documents.ProductDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/25/2024
 */
@Service
public class ProductCommandHandler implements ICommandHandler<CreateProductCommand> {
    private final IElasticsearchService<ProductDocument> elasticsearchService;

    @Autowired
    public ProductCommandHandler(IElasticsearchService<ProductDocument> elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    @Override
    public void execute(CreateProductCommand command) {
        return;
    }
}
