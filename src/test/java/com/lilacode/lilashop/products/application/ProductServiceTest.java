package com.lilacode.lilashop.products.application;

import com.lilacode.lilashop.products.domain.model.Product;
import com.lilacode.lilashop.products.infrastructure.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
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

    private static final String TEST_PRODUCT_NAME = "Test Product";

    @InjectMocks
    ProductService underTest;
    
    @Mock
    ProductRepository productRepository;

    @Test
    void itSavesProduct() {
        var product = Product.create(TEST_PRODUCT_NAME,  BigDecimal.TEN);

        given(productRepository.save(product))
                .willReturn(Mono.just(new Product(1L, TEST_PRODUCT_NAME, BigDecimal.TEN)));

        StepVerifier.create(underTest.save(product))
                .expectNextMatches(p -> p.name().equals(product.name()))
                .expectComplete();

        verify(productRepository, times(1))
                .save(any(Product.class));
    }

    @Test
    void itFindsAllProducts() {
        var product = Product.create(TEST_PRODUCT_NAME,  BigDecimal.TEN);

        given(productRepository.findAll())
                .willReturn(Flux.just(product));

        StepVerifier.create(underTest.getAllProducts())
                .expectNextMatches(p -> p.name().equals(TEST_PRODUCT_NAME))
                .expectComplete();

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void itFindsAllProductsWithSorting() {
        var product1 = new Product(1L, "Product A", BigDecimal.TEN);
        var product2 = new Product(2L, "Product B", BigDecimal.ONE);

        given(productRepository.findAll(Sort.by(Sort.Direction.ASC, "name")))
                .willReturn(Flux.just(product1, product2));

        StepVerifier.create(underTest.getAllProducts(Sort.by(Sort.Direction.ASC, "name")))
                .expectNextMatches(p -> p.name().equals("Product A"))
                .expectNextMatches(p -> p.name().equals("Product B"))
                .expectComplete();

        verify(productRepository, times(1))
                .findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Test
    void itFindsAProductById() {
        var product = new Product(1L, TEST_PRODUCT_NAME, BigDecimal.TEN);

        given(productRepository.findById(1L))
                .willReturn(Mono.just(product));

        StepVerifier.create(underTest.getProductById(1L))
                .expectNextMatches(p -> p.id() == 1L && p.name().equals(TEST_PRODUCT_NAME))
                .expectComplete();

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void itReturnsEmptyWhenNoProductFoundById() {
        given(productRepository.findById(2L))
                .willReturn(Mono.empty());

        StepVerifier.create(underTest.getProductById(2L))
                .expectNextCount(0)
                .expectComplete();

        verify(productRepository, times(1)).findById(2L);
    }

    @Test
    void itDeletesAProductById() {
        var productId = 1L;

        given(productRepository.deleteById(productId))
                .willReturn(Mono.empty());

        StepVerifier.create(underTest.deleteById(productId))
                .expectComplete();

        verify(productRepository, times(1)).deleteById(productId);
    }
    
    

}