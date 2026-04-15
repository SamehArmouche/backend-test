package com.example.productapi.service.impl;

import com.example.productapi.model.ProductDetail;
import com.example.productapi.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final RestTemplate restTemplate;
    
    @Value("${external.api.url}")
    private String baseUrl;
    

    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Obtiene los IDs de productos similares
     */
    private List<String> getSimilarIds(String productId) {
        try {
            String url = baseUrl + "/product/" + productId + "/similarids";
            String[] ids = restTemplate.getForObject(url, String[].class);
            return ids != null ? Arrays.asList(ids) : Collections.emptyList();
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Similar IDs not found for product {}", productId);
            return Collections.emptyList();
        } catch (Exception e) {
            logger.error("Error fetching similar IDs for product {}: {}", productId, e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene el detalle de un producto
     */
    private ProductDetail getProductById(String productId) {
        String url = baseUrl + "/product/" + productId;
        try {
            return restTemplate.getForObject(url, ProductDetail.class);
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Product {} not found", productId);
            return null;
        } catch (Exception e) {
            logger.error("Error fetching product {}: {}", productId, e.getMessage());
            return null;
        }
    }
    /**
     * Obtiene los productos similares completos
     */
    @Override
    public List<ProductDetail> getSimilarProducts(String productId) {
        return getSimilarIds(productId).stream()
                .map(this::getProductById)
                .filter(product -> product != null)
                .toList();
    }
}