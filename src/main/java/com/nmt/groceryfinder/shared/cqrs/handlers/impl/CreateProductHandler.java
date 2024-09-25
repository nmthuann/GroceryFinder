package com.nmt.groceryfinder.shared.cqrs.handlers.impl;

import com.nmt.groceryfinder.shared.cqrs.commands.CreateProductCommand;
import com.nmt.groceryfinder.shared.cqrs.handlers.ICommandHandler;
import com.nmt.groceryfinder.shared.elasticsearch.ProductDocument;
import com.nmt.groceryfinder.shared.elasticsearch.ProductDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/25/2024
 */
@Component
public class CreateProductHandler implements ICommandHandler<CreateProductCommand> {

    private final ProductDocumentRepository productDocumentRepository;

    @Autowired
    public CreateProductHandler(ProductDocumentRepository productDocumentRepository) {
        this.productDocumentRepository = productDocumentRepository;
    }


    @Override
    public void execute(CreateProductCommand command) {
        ProductDocument productDocument = new ProductDocument();
        productDocument.setProductName(command.productName());
        productDocument.setSlug(command.slug());
        productDocument.setDescription(command.description());
        this.productDocumentRepository.save(productDocument);
    }
}
