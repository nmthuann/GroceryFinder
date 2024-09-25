package com.nmt.groceryfinder.shared.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/22/2024
 */
@Document(indexName = "products")
@Getter
@Setter
public class ProductDocument {

    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Text)
    private String productName;

    @Field(type = FieldType.Text)
    private String slug;

    @Field(type = FieldType.Text)
    private String productLine;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Boolean)
    private Boolean status;

    @Field(type = FieldType.Boolean)
    private Boolean isDeleted = false;

    @Field(type = FieldType.Integer)
    private Integer prioritySort = 0;
}
