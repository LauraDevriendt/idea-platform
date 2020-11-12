package com.coaching.salesplatform.products;

import com.coaching.salesplatform.errors.NotFoundException;
import com.coaching.salesplatform.errors.NotValidException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;


    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product getProduct(Long id) {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("product not found");
        }
        return product.get();
    }

    public Product updateProduct(Product product, Long id) {
        getProduct(id);
        product.setId(id);
        return repository.save(product);
    }

    public Product verifyAndAddProduct(Product product) {
        Optional<Product> productByName = repository.findByName(product.getName());
        if (productByName.isPresent()){
            throw new NotValidException("product already exists with that name");
        }

        return  repository.saveAndFlush(product);
    }
}
