package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Address;
import org.upgrad.repositories.AddressRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address getAddressById(Integer addressId) {
        return addressRepository.getAddressById(addressId);
    }
}
