package com.lilacode.lilashop.products;

import com.lilacode.lilashop.products.application.ProductService;
import com.lilacode.lilashop.products.domain.model.Product;
import com.lilacode.lilashop.products.infrastructure.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @Test
    void testGetAllProducts() {
        var sampleProduct = new Product(1L, "Reactive Product", BigDecimal.TEN);
        var productDto = new com.lilacode.lilashop.api.model.Product();
        productDto.setId("1");
        productDto.setName("Reactive Product");
        productDto.setPrice(BigDecimal.TEN.floatValue());

        given(productService.getAllProducts()).willReturn(Flux.just(sampleProduct));
        given(productMapper.toDto(sampleProduct)).willReturn(productDto);

        webTestClient.get()
                .uri("/api/products")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBodyList(com.lilacode.lilashop.api.model.Product.class)
                .hasSize(1)
                .consumeWith(response -> {
                    com.lilacode.lilashop.api.model.Product dto = response.getResponseBody().get(0);
                    assertEquals(Long.valueOf(dto.getId()), sampleProduct.id());
                    assertEquals(dto.getName(), sampleProduct.name());
                    assertEquals(dto.getPrice(), sampleProduct.price().floatValue());
                });

        Mockito.verify(productService, Mockito.times(1)).getAllProducts();
    }
}