package com.coaching.salesplatform.sales.saleLine;

import com.coaching.salesplatform.errors.NotFoundException;
import com.coaching.salesplatform.errors.NotValidException;
import com.coaching.salesplatform.products.Product;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SaleLineService {

    private final SaleLineRepository repository;


    public SaleLineService(SaleLineRepository repository) {
        this.repository = repository;
    }

    public SaleLine getSaleLine(Long id) {
        Optional<SaleLine> saleLine = repository.findById(id);
        if (saleLine.isEmpty()) {
            throw new NotFoundException("saleLine not found");
        }
        return saleLine.get();
    }

    public SaleLine updateSaleLine(SaleLine saleLine, Long id) {
        getSaleLine(id);
        saleLine.setId(id);
        return repository.save(saleLine);
    }
}
