package com.example.productapi.controller;

import com.example.productapi.model.ProductDetail;
import com.example.productapi.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Devuelve los productos similares completos
     * GET /product/{productId}/similar
     */
    @GetMapping("/{productId}/similar")
    public ResponseEntity<?> getSimilarProducts(@PathVariable String productId) {
        List<ProductDetail> similarProducts = productService.getSimilarProducts(productId);

        if (similarProducts.isEmpty()) {
            logger.warn("No similar products found for ID {}", productId);
            return ResponseEntity.status(404).body("Product Not found");
        }

        return ResponseEntity.ok(similarProducts);
    }
}