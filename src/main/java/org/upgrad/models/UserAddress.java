package org.upgrad.models;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="user_address")
public class UserAddress {



    public  UserAddress(){

    }

    public UserAddress(User user,Address address,String type){
        this.user = user;
        this.address = address;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type")
    private String type;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public Integer getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(Integer user_id) {
//        this.user_id = user_id;
//    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress_id() {
        return address;
    }

    public void setAddress_id(Address address_id) {
        this.address = address_id;
    }

//    public Integer getAddress_id() {
//        return address_id;
//    }
//
//    public void setAddress_id(Integer address_id) {
//        this.address_id = address_id;
//    }





}
