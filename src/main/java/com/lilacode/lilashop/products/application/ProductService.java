package com.lilacode.lilashop.products.application;

import com.lilacode.lilashop.products.common.exception.ResourceNotFoundException;
import com.lilacode.lilashop.products.domain.model.Product;
import com.lilacode.lilashop.products.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Mono<Product> save(Product product) {

        return productRepository.save(product);
    }

    public Flux<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public Flux<Product> getAllProducts(Sort sort) {

        return productRepository.findAll(sort);
    }

    public Mono<Product> getProductById(long productId) {

        return productRepository.findById(productId)
                .switchIfEmpty(getError(productId));
    }

    public Mono<Void> deleteById(long productId) {

        return productRepository.deleteById(productId)
                .switchIfEmpty(getError(productId));
    }

    private <T> Mono<T> getError(long productId) {
        return Mono.error(
                new ResourceNotFoundException(String.format(" Product with id '%s'", productId)));
    }
}
