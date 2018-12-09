package org.upgrad.repositories;

import org.upgrad.models.Address;
import org.upgrad.models.States;
import org.upgrad.models.UserAddress;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// This repository interface is responsible for the interaction between the user service with the user database

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {

    // For adding address in table.
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO Address (flat_buil_number,locality,city,zipcode,state_id) VALUES (?1,?2,?3,?4,?5)")
    Integer addAddress(String flat_buil_number, String locality, String city,String zipcode, Integer state_id );

    // For adding user details in users table.
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO User_Address (type,user_id,address_id) VALUES (?1,?2,?3)")
    Integer addUserAddress(String type, Integer user_id , Integer address_id);

    //This returns the max(id) of the address.
    @Query(nativeQuery = true,value = "SELECT max(id) FROM ADDRESS ")
    Integer countAddress();

    //This selects state Name for the state_id.
    @Query(nativeQuery = true,value = "SELECT *  FROM ADDRESS where id = ?1 ")
    Address findAddressById(Integer id);

    // Method to update details for particular user.
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="UPDATE ADDRESS SET flat_buil_number =?1 , locality=?2  , city=?3 , zipcode=?4, state_id=?5 WHERE id=?6")
    Integer updateAddressById( String flat_buil_number, String locality, String city , String zipcode, Integer state_id, Integer id);

    // Method to update details for particular user.
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="DELETE FROM ADDRESS WHERE id =?1")
    Integer deleteAddressById( Integer id);

    // Method to update details for particular user.
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="DELETE FROM User_Address WHERE address_id =?1")
    Integer deleteUserAddressById( Integer id);

    // Method to get Permanent Address details for a user
    @Query(nativeQuery = true,value = "SELECT address_id  FROM USER_ADDRESS where type = 'prem' and user_id = ?1 ")
    Iterable<Integer> getPermAdd(Integer id);

}