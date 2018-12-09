package org.upgrad.services;

import org.upgrad.models.Address;
import org.upgrad.models.States;

public interface AddressService {

    Iterable<States> getAllStates() ;



    States checkValidState(Integer id);


    Integer addAddress(Address address);


    Integer countAddress();


    Integer addUserAddress(String temp, Integer user_id, Integer address_id) ;

    Address getaddressById( Integer addressId);

    Address getAddressById(Integer addressId);


    Boolean getAddress( Integer addressId);


    Integer updateAddressById (String flat_build_num , String locality, String city, String zipcode , Integer state_id , Integer id);


    Integer deleteAddressById (Integer id );


    Integer deleteUserAddressById(Integer id);


    Iterable<Address> getPermAddress(Integer userId) ;



}
