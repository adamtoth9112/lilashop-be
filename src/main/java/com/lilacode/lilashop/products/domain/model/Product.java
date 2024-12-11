package com.lilacode.lilashop.products.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("PRODUCTS")
public record Product(
        @Id Long id,
        String name,
        BigDecimal price) {

    public static Product create(String name, BigDecimal price) {
        return new Product(null, name, price);
    }
}
