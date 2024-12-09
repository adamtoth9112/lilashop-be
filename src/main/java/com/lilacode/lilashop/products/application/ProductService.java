package com.lilacode.lilashop.products.application;

import com.lilacode.lilashop.products.domain.model.Product;
import com.lilacode.lilashop.products.infrastructure.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Mono<Product> save(Product product) {

        return productRepository.save(product);
    }

    public Flux<Product> findAll() {

        return productRepository.findAll();
    }
}
