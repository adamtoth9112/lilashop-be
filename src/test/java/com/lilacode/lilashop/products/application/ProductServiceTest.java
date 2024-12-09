package com.lilacode.lilashop.products.application;

import com.lilacode.lilashop.products.domain.model.Product;
import com.lilacode.lilashop.products.infrastructure.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService underTest;
    
    @Mock
    ProductRepository productRepository;

    @Test
    void itSavesProduct() {
        var product = Product.create("Test Product",  BigDecimal.TEN);

        given(productRepository.save(product))
                .willReturn(Mono.just(new Product(1L, "Test Product", BigDecimal.TEN)));

        StepVerifier.create(underTest.save(product))
                .expectNextMatches(p -> p.name().equals(product.name()))
                .expectComplete();

        verify(productRepository, times(1))
                .save(any(Product.class));
    }

    @Test
    void itFindsAllProducts() {
        var product = Product.create("Test Product",  BigDecimal.TEN);

        given(productRepository.findAll())
                .willReturn(Flux.just(product));

        StepVerifier.create(underTest.findAll())
                .expectNextMatches(p -> p.name().equals(product.name()))
                .expectComplete();

        verify(productRepository, times(1)).findAll();
    }
}