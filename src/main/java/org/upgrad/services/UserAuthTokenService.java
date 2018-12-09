package org.upgrad.services;

import org.upgrad.models.UserAuthToken;

/*
 * This UserAuthTokenService interface gives the list of all the service that exist in the userAuthToken service implementation class.
 * Controller class will be calling the service methods by this interface.
 */
public interface UserAuthTokenService {

    void addAccessToken(Integer userId, String accessToken);

    void removeAccessToken(String accessToken);

    UserAuthToken isUserLoggedIn(String accessToken);

    Integer getUserId(String accessToken);
}
