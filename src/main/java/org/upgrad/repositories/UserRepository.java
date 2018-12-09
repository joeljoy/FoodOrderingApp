package org.upgrad.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.upgrad.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.upgrad.models.UserAuthToken;

// This repository interface is responsible for the interaction between the user service with the user database

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(nativeQuery = true,value="SELECT PASSWORD FROM USERS WHERE contact_number=?1")
    String findUserPassword(String contactNumber);

    @Query(nativeQuery = true,value = "SELECT * FROM USERS WHERE contact_number=?1")
    User findUser(String contactNumber);

    @Query(nativeQuery = true,value = "SELECT * FROM USERS WHERE contact_number=?1")
    void addUser(User newuser);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users SET firstname = ?1, lastname=?2 where id=?3")
    void updateddetails(String firstname, String lastname,long id);

    @Query(nativeQuery = true,value = "SELECT * FROM USERS WHERE id=?1")
    User findUserbyID(long userid);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="UPDATE USERS SET password =?1  WHERE id=?2")
    Integer updatePassword(String password, Integer id);


    @Query(nativeQuery = true,value="SELECT * FROM USERS WHERE id=?1")
    User findPasswordById(long id);
}

