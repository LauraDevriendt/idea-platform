package com.coaching.salesplatform.customer;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer getCustomer(Long id) {
        Optional<Customer> customer = repository.findById(id);
        if(customer.isEmpty()){
            throw new CustomerNotFoundException("customer not found");
        }
        return customer.get();
    }

    public Customer updateCustomer(Customer customer, Long id) {

        Optional<Customer> customerInDataBase = repository.findById(id);

        if (!customerInDataBase.isPresent())
            throw new CustomerNotFoundException("customer not found");

        return repository.save(customer);
    }

    public Customer verifyAndAddCustomer(Customer customer) {
        Optional<Customer> customerByName = repository.findByFirstNameAndLastName(customer.getFirstName(), customer.getLastName());
        if (customerByName.isPresent()){
            throw new CustomerNotValidException("customer already exists with that name");
        }

        return  repository.saveAndFlush(customer);
    }
}
