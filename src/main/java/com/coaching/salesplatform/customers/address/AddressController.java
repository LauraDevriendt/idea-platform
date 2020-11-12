package com.coaching.salesplatform.customers.address;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/addresses")
public class AddressController {

    private final AddressRepository repository;
    private final AddressService service;


    public AddressController(AddressRepository repository, AddressService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<Address>> getAddresses() {
        List<Address> addresses =  repository.findAll();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        return new ResponseEntity<>(service.getAddress(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address, @PathVariable Long id) {
        return new ResponseEntity<>(service.updateAddress(address, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Address with id " + id + " is deleted", HttpStatus.OK);
    }
}
