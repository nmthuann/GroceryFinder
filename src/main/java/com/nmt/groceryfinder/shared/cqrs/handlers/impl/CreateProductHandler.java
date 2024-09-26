package com.nmt.groceryfinder.shared.cqrs.handlers.impl;

import com.nmt.groceryfinder.shared.cqrs.commands.CreateProductCommand;
import com.nmt.groceryfinder.shared.cqrs.handlers.ICommandHandler;
import com.nmt.groceryfinder.shared.elasticsearch.ProductDocument;
import com.nmt.groceryfinder.shared.elasticsearch.ProductDocumentRepository;
import com.nmt.groceryfinder.utils.SlugUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/25/2024
 */
@Service
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
        productDocument.setSlug(SlugUtil.createSlug(command.productName()));
        productDocument.setDescription(command.description());
        this.productDocumentRepository.save(productDocument);
    }
}
