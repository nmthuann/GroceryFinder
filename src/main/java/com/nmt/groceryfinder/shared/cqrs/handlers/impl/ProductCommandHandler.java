package com.nmt.groceryfinder.shared.cqrs.handlers.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.nmt.groceryfinder.shared.cqrs.commands.CreateProductCommand;
import com.nmt.groceryfinder.shared.cqrs.handlers.ICommandHandler;
import com.nmt.groceryfinder.shared.elasticsearch.ProductDocument;
import com.nmt.groceryfinder.utils.SlugUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/25/2024
 */
@Service
public class ProductCommandHandler implements ICommandHandler<CreateProductCommand> {


    private final ElasticsearchClient elasticsearchClient;

    @Autowired
    public ProductCommandHandler(
            ElasticsearchClient elasticsearchClient
    ) {

        this.elasticsearchClient = elasticsearchClient;
    }


    @Override
    public void execute(CreateProductCommand command) {
        ProductDocument productDocument = new ProductDocument();
        productDocument.setProductName(command.productName());
        productDocument.setSlug(SlugUtil.createSlug(command.productName()));
        productDocument.setDescription(command.description());
        // this.productDocumentRepository.save(productDocument);
    }
}
