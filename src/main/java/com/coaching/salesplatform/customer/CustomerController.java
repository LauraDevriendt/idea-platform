package com.coaching.salesplatform.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerRepository repository;
    private final CustomerService service;
    private final AddressService addressService;

    public CustomerController(CustomerRepository repository, CustomerService service, AddressService addressService) {
        this.repository = repository;
        this.service = service;
        this.addressService = addressService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers =  repository.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(service.getCustomer(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody final Customer customer) {
        List<Address> addresses = new ArrayList<>();
        for (Address address : customer.getAddresses()) {
            Address addressDatabase = addressService.addAddress(address);
            addresses.add(addressDatabase);
        }
        customer.setAddresses(addresses);

        return new ResponseEntity<>(service.verifyAndAddCustomer(customer), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        List<Address> addresses = new ArrayList<>();
        for (Address address : customer.getAddresses()) {
            Address addressDatabase = addressService.addAddress(address);
            addresses.add(addressDatabase);
        }
        customer.setAddresses(addresses);
        return new ResponseEntity<>(service.updateCustomer(customer, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Customer with id " + id + " is deleted", HttpStatus.OK);
    }
}
