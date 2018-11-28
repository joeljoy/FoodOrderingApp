package org.upgrad.services;

import org.upgrad.models.User;
import org.upgrad.models.UserAuthToken;

import java.util.Optional;

/*
 * This UserService interface gives the list of all the service that exist in the user service implementation class.
 * Controller class will be calling the service methods by this interface.
 */
public interface UserService {

    String findUserPassword(String contactNumber);

    User findUser(String contactNumber);
}
