package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.Address;
import org.upgrad.models.States;
import org.upgrad.models.UserAuthToken;
import org.upgrad.services.AddressService;
import org.upgrad.services.UserAuthTokenService;


@RestController
@RequestMapping("")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserAuthTokenService userAuthTokenService;

    @PostMapping("/address")
    @CrossOrigin
    public ResponseEntity<?> addaddress(@RequestParam String flatBuildnumber,@RequestParam String locality, @RequestParam String city, @RequestParam Integer stateid, @RequestParam(required = false) String zipcode, @RequestParam(required = false) String type, @RequestHeader String accessToken){
        String msg = "";
        HttpStatus httpcode = HttpStatus.OK;
        if(userAuthTokenService.isUserLoggedIn(accessToken) == null){
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else if(userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt()!=null){
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else
            {
            Integer userId = userAuthTokenService.getUserId(accessToken);
            if(zipcode.length() == 6 && zipcode.matches("[0-9]+"))
            {
                States state = addressService.checkValidState(stateid);

                int addressid = addressService.countAddress()+1;
                String addrtype = "temp";

                Address address = new Address(addressid,flatBuildnumber,locality,city,zipcode,state);

                if(type.equalsIgnoreCase("perm")){
                    addrtype = "perm";
                }

                addressService.addAddress(address);
                addressService.addUserAddress(addrtype,userId,addressid);
                httpcode = HttpStatus.CREATED;
                msg = "Address has been saved successfully!";

            } else{
                msg = "Invalid zipcode!";
                httpcode = HttpStatus.BAD_REQUEST;
            }
                return  new ResponseEntity<>(msg,httpcode);

        }
    }



    @GetMapping("/address/user")
    @CrossOrigin
    public ResponseEntity<?> getAllPermanentAddress(@RequestHeader String accessToken) {

        String msg = "" ;
        HttpStatus httpStatus = HttpStatus.OK ;

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);

        if (usertoken == null) {
            msg = "Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        }
        else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null ) {
            msg = "You have already logged out. Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } else {
            Integer userId = usertoken.getUser().getId();
            System.out.println("User logged in for whom address request is made is "+userId);

            if ( addressService.getPermAddress(userId)  == null )
            {
                msg = "No permanent address found!" ;
                httpStatus = HttpStatus.BAD_REQUEST ;
            }
            else
            {
                    return new ResponseEntity<>(addressService.getPermAddress(userId), HttpStatus.OK);

            }
        }
        return new ResponseEntity<>(msg , httpStatus);
    }



    @PutMapping("/address/{addressId}")
    @CrossOrigin
    public ResponseEntity<?> updateAddressById(@PathVariable Integer addressId , @RequestParam(required = false) String flatBuildNo , @RequestParam(required = false) String locality , @RequestParam(required = false) String city , @RequestParam(required = false) String zipcode , @RequestParam(required = false) Integer stateId , @RequestHeader String accessToken) {

        String message = "" ;
        HttpStatus httpStatus = HttpStatus.OK ;

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);


        if (usertoken == null) {
            message = "Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        }
        else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null ) {
            message = "You have already logged out. Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } else {
            Integer userId = usertoken.getUser().getId();


            if (zipcode != null ){
                if (! ( zipcode.length() == 6 && zipcode.matches("[0-9]+") )) {
                    return new ResponseEntity<>("Invalid zipcode!" , HttpStatus.BAD_REQUEST);
                }
            }



            Address add = addressService.getaddressById(addressId);

            if (addressService.getAddress(addressId) == false) {
                message = "No address with this address id!";
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                /*
                Get the current details if nothing is supplied
                 */
                if (flatBuildNo == null)
                    flatBuildNo = add.getFlatbuildNumber();

                if (locality == null)
                    locality = add.getLocality();

                if (city == null)
                    city = add.getCity();

                if (zipcode == null)
                    zipcode = add.getZipcode();

                if (stateId == null)
                    stateId = add.getState().getId() ;


                addressService.updateAddressById(flatBuildNo, locality, city, zipcode, stateId, addressId);

                message = "Address has been updated successfully!";
                httpStatus = HttpStatus.OK ;
            }
        }
        return new ResponseEntity<>(message , httpStatus);
    }


    @DeleteMapping("/address/{addressId}")
    @CrossOrigin
    public ResponseEntity<?> deleteAddressById(@PathVariable Integer addressId , @RequestHeader String accessToken) {

        String msg = "";
        HttpStatus httpStatus = HttpStatus.OK;

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);


        if (usertoken == null) {
            msg = "Please Login first to access this endpoint!";
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt()!= null ) {
            msg = "You have already logged out. Please Login first to access this endpoint!";
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {

            boolean add = addressService.getAddress(addressId);

            if (add == false) {
                msg = "No address with this address id!";
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {


                addressService.deleteAddressById(addressId) ;
                addressService.deleteUserAddressById(addressId);

                msg = "Address has been deleted successfully!" ;
                httpStatus = HttpStatus.OK ;
            }
        }
        return new ResponseEntity<>(msg , httpStatus);
    }



    @GetMapping("/states")
    public ResponseEntity<?> getAllStateDetails() {
        return new ResponseEntity<>( addressService.getAllStates() , HttpStatus.OK);
    }



}