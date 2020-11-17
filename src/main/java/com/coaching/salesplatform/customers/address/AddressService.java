package com.coaching.salesplatform.customers.address;

import com.coaching.salesplatform.errors.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Optional<Address> findAddress(Address address) {
        return repository.findByStreetNameAndNumberAndCityAndZipcode(address.getStreetName(), address.getNumber(), address.getCity(), address.getZipcode());
    }

    public Address getAddress(Long id) {
        Optional<Address> address = repository.findById(id);
        if (address.isEmpty()) {
            throw new NotFoundException("address not found");
        }
        return address.get();
    }

    public Address updateAddress(Address address, Long id) {
        getAddress(id);
        address.setId(id);
        return repository.save(address);
    }

    public void removeAddress(Address address) {
        repository.delete(address);
    }
}
