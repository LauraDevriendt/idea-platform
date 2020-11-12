package com.coaching.salesplatform.sales.saleLine;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/saleLines")
public class SaleLineController {

    private final SaleLineRepository repository;
    private final SaleLineService service;

    public SaleLineController(SaleLineRepository repository, SaleLineService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<SaleLine>> getSaleLines() {
        List<SaleLine> saleLines =  repository.findAll();
        return new ResponseEntity<>(saleLines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleLine> getSaleLine(@PathVariable Long id) {
        return new ResponseEntity<>(service.getSaleLine(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SaleLine> createSaleLine(@Valid @RequestBody final SaleLine saleLine) {
        return new ResponseEntity<>(repository.save(saleLine), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleLine> updateSaleLine(@RequestBody SaleLine saleLine, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateSaleLine(saleLine, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSaleLine(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("SaleLine with id " + id + " is deleted", HttpStatus.OK);
    }
}
