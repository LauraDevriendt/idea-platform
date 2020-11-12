package com.coaching.salesplatform.sales.sale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/sales")
public class SaleController {

    private final SaleRepository repository;
    private final SaleService service;

    public SaleController(SaleRepository repository, SaleService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<Sale>> getSales() {
        List<Sale> sales =  repository.findAll();
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSale(@PathVariable Long id) {
        return new ResponseEntity<>(service.getSale(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(@Valid @RequestBody final Sale sale) {
        Sale sale1 = repository.save(sale);
        return new ResponseEntity<>( sale1, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@RequestBody Sale sale, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateSale(sale, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Sale with id " + id + " is deleted", HttpStatus.OK);
    }
}
