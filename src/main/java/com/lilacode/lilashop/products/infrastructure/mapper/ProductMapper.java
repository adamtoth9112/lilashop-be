package com.lilacode.lilashop.products.infrastructure.mapper;

import com.lilacode.lilashop.api.model.NewProduct;
import com.lilacode.lilashop.products.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    com.lilacode.lilashop.api.model.Product toDto(Product product);

    @Mapping(target = "id", ignore = true)
    Product toEntity(NewProduct product);
}
