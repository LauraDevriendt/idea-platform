package com.coaching.salesplatform.customer;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByStreetNameAndNumberAndCityAndZipcode(String streetName, Integer number, String city, String zipcode);
}
