package com.nmt.groceryfinder.shared.elasticsearch.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/22/2024
 */
@JsonIgnoreProperties(ignoreUnknown = true) // remove the undefined field
public record ProductDocument (
        @Field(type = FieldType.Text)
        String id,

        @Field(type = FieldType.Text)
        String productName,

        @Field(type = FieldType.Text)
        String slug,

        @Field(type = FieldType.Text)
        String productLine,

        @Field(type = FieldType.Text)
        String productSpecs,

        @Field(type = FieldType.Text)
        String description,

        @Field(type = FieldType.Boolean)
        Boolean status,

        @Field(type = FieldType.Boolean)
        Boolean isDeleted,

        @Field(type = FieldType.Integer)
        Integer prioritySort,

        @Field(type = FieldType.Text)
        String brandName,

        @Field(type = FieldType.Text)
        String categoryName,

        @Field(type = FieldType.Text)
        String supplierName
) {
}
