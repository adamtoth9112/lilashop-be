package com.lilacode.lilashop.products;

import com.lilacode.lilashop.api.ApiApi;
import com.lilacode.lilashop.api.model.NewProduct;
import com.lilacode.lilashop.api.model.Product;
import com.lilacode.lilashop.products.application.ProductService;
import com.lilacode.lilashop.products.infrastructure.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ProductsController implements ApiApi {

    private final ProductService productService;
    private final ProductMapper productMapper;


    @Override
    public Flux<Product> getProducts(ServerWebExchange exchange) {

        return productService.getAllProducts()
                .map(productMapper::toDto);
    }

    @Override
    public Mono<Void> deleteProduct(String id, ServerWebExchange exchange) {

        return productService.deleteById(Long.parseLong(id));
    }

    @Override
    public Mono<Product> getProductById(String id, ServerWebExchange exchange) {

        return productService.getProductById(Long.parseLong(id))
                .map(productMapper::toDto);
    }

    @Override
    public Mono<Void> updateProduct(String id, Mono<NewProduct> product, ServerWebExchange exchange) {

        return ApiApi.super.updateProduct(id, product, exchange);
    }

    @Override
    public Mono<Product> createProduct(Mono<NewProduct> product, ServerWebExchange exchange) {

        return product.map(productMapper::toEntity)
                .flatMap(productService::save)
                .map(productMapper::toDto);
    }
}
