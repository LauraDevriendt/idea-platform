package com.coaching.salesplatform.sales.sale;

import com.coaching.salesplatform.errors.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository repository;


    public SaleService(SaleRepository repository) {
        this.repository = repository;
    }

    public Sale getSale(Long id) {
        Optional<Sale> sale = repository.findById(id);
        if (sale.isEmpty()) {
            throw new NotFoundException("sale not found");
        }
        return sale.get();
    }

    public Sale updateSale(Sale sale, Long id) {
        getSale(id);
        sale.setId(id);
        return repository.save(sale);
    }
}
