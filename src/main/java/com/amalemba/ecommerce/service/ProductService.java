package com.amalemba.ecommerce.service;

import com.amalemba.ecommerce.model.Product;
import com.amalemba.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(int productId) {
        return  repository.findById(productId).orElse(null);
    }
}
