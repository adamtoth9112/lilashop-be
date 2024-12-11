package com.lilacode.lilashop.products.infrastructure.mapper;

import com.lilacode.lilashop.products.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    com.lilacode.lilashop.api.model.Product toDto(Product product);

    Product toEntity(com.lilacode.lilashop.api.model.Product product);
}
