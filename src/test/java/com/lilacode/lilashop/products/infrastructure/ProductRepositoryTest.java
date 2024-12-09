package com.lilacode.lilashop.products.infrastructure;

import com.lilacode.lilashop.products.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

@DataR2dbcTest
@ActiveProfiles("dev")
class ProductRepositoryTest {

    @Autowired
    ProductRepository underTest;

    @Test
    void itPersistsEntity() {
        var product = Product.create("Test Product", BigDecimal.TEN);

        StepVerifier.create(underTest.save(product))
                .expectNextMatches(savedProduct -> savedProduct.id() != null)
                .verifyComplete();
    }
}