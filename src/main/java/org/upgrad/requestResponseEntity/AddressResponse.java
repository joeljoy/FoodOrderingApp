package org.upgrad.requestResponseEntity;

import org.upgrad.models.States;

public class AddressResponse {
    private  String flat_build_number;
    private Integer id;
    private States state;
    private String city;
    private String locality;
    private String zipcode;


    public AddressResponse(Integer id, String flatbuildNumber, String locality, String city, String zipcode, States state) {
        System.out.println(flatbuildNumber+" "+locality+" "+city+" "+zipcode);
        this.flat_build_number = flat_build_number ;
        this.city = city ;
        this.locality = locality ;
        this.zipcode = zipcode ;
        this.state = state ;
    }


    public AddressResponse(){

    }

    public String getFlat_build_number() {
        return flat_build_number;
    }

    public void setFlat_build_number(String flat_build_number) {
        this.flat_build_number = flat_build_number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }



}
