package com.coaching.salesplatform.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Address addAddress(Address address) {
        Optional<Address> foundAddress = repository.findByStreetNameAndNumberAndCityAndZipcode(address.getStreetName(), address.getNumber(), address.getCity(), address.getZipcode());
        if (foundAddress.isEmpty()) {
            return repository.saveAndFlush(address);
        }
        return foundAddress.get();
    }
}
