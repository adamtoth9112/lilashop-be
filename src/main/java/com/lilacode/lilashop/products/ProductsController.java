package com.lilacode.lilashop.products;

import com.lilacode.lilashop.api.ProductsApi;
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
public class ProductsController implements ProductsApi {

    private final ProductService productService;
    private final ProductMapper productMapper;


    @Override
    public Flux<Product> productsGet(ServerWebExchange exchange) {

        return productService.getAllProducts()
                .map(productMapper::toDto);
    }

    @Override
    public Mono<Void> productsIdDelete(String id, ServerWebExchange exchange) {

        return productService.deleteById(Long.parseLong(id));
    }

    @Override
    public Mono<Product> productsIdGet(String id, ServerWebExchange exchange) {

        return productService.getProductById(Long.parseLong(id))
                .map(productMapper::toDto);
    }

    @Override
    public Mono<Void> productsIdPut(String id, Mono<Product> product, ServerWebExchange exchange) {

        return ProductsApi.super.productsIdPut(id, product, exchange);
    }

    @Override
    public Mono<Product> productsPost(Mono<Product> product, ServerWebExchange exchange) {

        return product.map(productMapper::toEntity)
                .flatMap(productService::save)
                .map(productMapper::toDto);
    }
}
