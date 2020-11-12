package com.coaching.salesplatform.products;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductRepository repository;
    private final ProductService service;

    public ProductController(ProductRepository repository, ProductService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products =  repository.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return new ResponseEntity<>(service.getProduct(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody final Product product) {
        return new ResponseEntity<>(service.verifyAndAddProduct(product), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateProduct(product, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Product with id " + id + " is deleted", HttpStatus.OK);
    }
}
