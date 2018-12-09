package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Address;

@Repository
public interface AddressRepository {

    @Query(nativeQuery = true, value = "SELECT * FROM address WHERE (id) = (?1)")
    Address getAddressById(Integer addressId);
}
