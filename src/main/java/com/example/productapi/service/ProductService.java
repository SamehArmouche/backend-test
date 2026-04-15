package com.example.productapi.service;

import com.example.productapi.model.ProductDetail;
import java.util.List;

public interface ProductService {

    /**
     * Devuelve la lista de productos similares completos (detalles) para un productId dado.
     *
     * @param productId el ID del producto
     * @return lista de productos similares
     */
    List<ProductDetail> getSimilarProducts(String productId);
}