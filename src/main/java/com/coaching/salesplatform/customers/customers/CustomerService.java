package com.coaching.salesplatform.customers.customers;

import com.coaching.salesplatform.customers.address.Address;
import com.coaching.salesplatform.customers.address.AddressService;
import com.coaching.salesplatform.errors.NotFoundException;
import com.coaching.salesplatform.errors.NotValidException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final AddressService addressService;

    public CustomerService(CustomerRepository repository, AddressService addressService) {
        this.repository = repository;
        this.addressService = addressService;
    }

    public Customer getCustomer(Long id) {
        Optional<Customer> customer = repository.findById(id);
        if(customer.isEmpty()){
            throw new NotFoundException("customer not found");
        }
        return customer.get();
    }

    public Customer updateCustomer(Customer customer, Long id) {
        getCustomer(id);
        customer.setId(id);
        return repository.save(customer);
    }

    public Customer verifyAndAddCustomer(Customer customer) {
        Optional<Customer> customerByName = repository.findByFirstNameAndLastName(customer.getFirstName(), customer.getLastName());
        if (customerByName.isPresent()){
            throw new NotValidException("customer already exists with that name");
        }

        return  repository.saveAndFlush(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customerInDatabase = getCustomer(id);
        List<Address> addresses = customerInDatabase.getAddresses();
        for (Address address: addresses) {
            if(address.getCustomers().size()==1){
                addressService.removeAddress(address);
            }
        }
        repository.deleteById(id);
    }
}
