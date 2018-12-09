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

import javax.swing.plaf.nimbus.State;

@RestController
@RequestMapping("")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserAuthTokenService userAuthTokenService;

    /*
    This API is for adding the address
     */
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
            if(zipcode.length() == 6 && zipcode.matches("[0-9]+")){
                States state = addressService.checkValidState(stateid);

                int addressid = addressService.countAddress()+1;
                String addrtype = "temp";

                Address address = new Address(addressid,flatBuildnumber,locality,city,zipcode,state);
                addressService.addAddress(address);

                if(type != null){
                    addrtype = type;
                    addressService.addUserAddress(addrtype,userId,addressid);
                    httpcode = HttpStatus.CREATED;
                    msg = "Address has been saved successfully!";
                }
                else{
                    msg = "Invalid zipcode!";
                    httpcode = HttpStatus.BAD_REQUEST;
                }


            }
                return  new ResponseEntity<>(msg,httpcode);

        }
    }


    /**
     * This is Get API that returns permanent address for the logged in user
     * @Header accessToken to identify if the user is logged in or not.
     * @return returns permanent address for the logged in user
     */
    @GetMapping("/address/user")
    @CrossOrigin
    public ResponseEntity<?> getAllPermanentAddress(@RequestHeader String accessToken) {

        String message = "" ;
        HttpStatus httpStatus = HttpStatus.OK ;

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);
        // Checking if user is logged in.
        if (null == usertoken) {
            message = "Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } // checking if user is not logged out.
        else if (null != userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() ) {
            message = "You have already logged out. Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } else {
            Integer userId = userAuthTokenService.getUserId(accessToken);

            if ( addressService.getPermAddress(userId)  == null )
            {
                message = "No permanent address found!" ;
                httpStatus = HttpStatus.BAD_REQUEST ;
            }
            else
            {
                if (null == addressService.getPermAddress(userId)) {
                    message = "No permanent address found!";
                    httpStatus = HttpStatus.BAD_REQUEST ;
                }
                else {
                    // Returns corresponding details in required JSON format
                    return new ResponseEntity<>(addressService.getPermAddress(userId), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(message , httpStatus);
    }


    /**
     * This is PUT API that updates address in database if address already exists
     * @pathVariable addressId  Id of address that is already present in DB
     * @param flatBuildNo flat/building number
     * @param locality  locality
     * @param city city name
     * @param zipcode valid 6 digit code
     * @param stateId state where the address belongs to
     * @Header accessToken to identify if the user is logged in or not
     * @return whether address is updated in DB or not
     */
    @PutMapping("/address/{addressId}")
    @CrossOrigin
    public ResponseEntity<?> updateAddressById(@PathVariable Integer addressId , @RequestParam(required = false) String flatBuildNo , @RequestParam(required = false) String locality , @RequestParam(required = false) String city , @RequestParam(required = false) String zipcode , @RequestParam(required = false) Integer stateId , @RequestHeader String accessToken) {

        String message = "" ;
        HttpStatus httpStatus = HttpStatus.OK ;

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);

        // Checking if user is logged in.
        if (null == usertoken) {
            message = "Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } // checking if user is not logged out.
        else if (null != userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() ) {
            message = "You have already logged out. Please Login first to access this endpoint!" ;
            httpStatus =  HttpStatus.UNAUTHORIZED ;
        } else {
            Integer userId = userAuthTokenService.getUserId(accessToken);

            // Zipcode Validation check
            if (zipcode != null ){
                if (! ( zipcode.length() == 6 && zipcode.matches("[0-9]+") )) {
                    return new ResponseEntity<>("Invalid zipcode!" , HttpStatus.BAD_REQUEST);
                }
            }

            // Check if address exists for supplied addressId
            Boolean  astatus = addressService.getAddress(addressId);
            Address add = addressService.getaddressById(addressId);

            if (false == astatus) {
                message = "No address with this address id!";
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {
                // If value is not supplied as parameters than take previous values.
                if (null == flatBuildNo)
                    flatBuildNo = add.getFlatbuildNumber();

                if (null == locality)
                    locality = add.getLocality();

                if (null == city)
                    city = add.getCity();

                if (null == zipcode)
                    zipcode = add.getZipcode();

                if (null == stateId)
                    stateId = add.getState().getId() ;

                // Update Address
                addressService.updateAddressById(flatBuildNo, locality, city, zipcode, stateId, addressId);

                message = "Address has been updated successfully!";
                httpStatus = HttpStatus.OK ;
            }
        }
        return new ResponseEntity<>(message , httpStatus);
    }

    /**
     * This is DELETE API that deletes address in database if address already exists.
     * @pathVariable addressId  Id of address that is already present in DB.
     * @Header accessToken to identify if the user is logged in or not.
     * @return whether address is deleted in DB or not.
     */
    @DeleteMapping("/address/{addressId}")
    @CrossOrigin
    public ResponseEntity<?> deleteAddressById(@PathVariable Integer addressId , @RequestHeader String accessToken) {

        String message = "";
        HttpStatus httpStatus = HttpStatus.OK;

        UserAuthToken usertoken = userAuthTokenService.isUserLoggedIn(accessToken);

        // Checking if user is logged in.
        if (null == usertoken) {
            message = "Please Login first to access this endpoint!";
            httpStatus = HttpStatus.UNAUTHORIZED;
        } // checking if user is not logged out.
        else if (null != userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() ) {
            message = "You have already logged out. Please Login first to access this endpoint!";
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            // Check if address exists for supplied addressId
            Boolean add = addressService.getAddress(addressId);

            if (false == add ) {
                message = "No address with this address id!";
                httpStatus = HttpStatus.BAD_REQUEST;
            } else {

                // Delete address from both tables.
                addressService.deleteAddressById(addressId) ;
                addressService.deleteUserAddressById(addressId);

                message = "Address has been deleted successfully!" ;
                httpStatus = HttpStatus.OK ;
            }
        }
        return new ResponseEntity<>(message , httpStatus);
    }



    /**
     * This is Get API that returns all states that are present in DB.
     * @return returns all states.
     */
    @GetMapping("/states")
    public ResponseEntity<?> getPermanentAddress() {
        return new ResponseEntity<>( addressService.getAllStates() , HttpStatus.OK);
    }



}