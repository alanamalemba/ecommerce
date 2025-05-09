package com.amalemba.ecommerce.controller;

import com.amalemba.ecommerce.model.Product;
import com.amalemba.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;

    @RequestMapping("/")
    public String greet() {
        return "Hello Alan!";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {

        Product product = service.getProductById(productId);

        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            Product savedProduct = service.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {

        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProductById(
            @PathVariable int productId,
            @RequestPart Product product, @RequestPart MultipartFile imageFile
    ) {
        try {
            Product updatedProduct = service.updateProductById(productId, product, imageFile);

            if (updatedProduct != null)
                return new ResponseEntity<>("Product updated successully", HttpStatus.OK);
            else
                return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable int productId) {
        try {
            String successMessage = "Product deleted successfully";

            Product targetProduct = service.getProductById((productId));

            if (targetProduct == null) {
                return new ResponseEntity<>("Product does not exist", HttpStatus.NOT_FOUND);
            }

            service.deleteProductById(productId);
            return new ResponseEntity<>(successMessage, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
