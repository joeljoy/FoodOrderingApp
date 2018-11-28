package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.upgrad.models.User;
import org.upgrad.models.UserAuthToken;
import org.upgrad.repositories.UserAuthTokenRepository;
import org.upgrad.repositories.UserRepository;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserAuthTokenServiceImpl implements UserAuthTokenService {

    private final UserAuthTokenRepository userAuthTokenRepository;
    private final UserRepository userRepository;

    public UserAuthTokenServiceImpl(UserAuthTokenRepository userAuthTokenRepository, UserRepository userRepository) {
        this.userAuthTokenRepository = userAuthTokenRepository;
        this.userRepository = userRepository;
    }

    // This method is used to add access token details in the database
    @Override
    public void addAccessToken(Integer userId, String accessToken) {
        Optional<User> user = userRepository.findById(userId);
        Date date =new Date();
        UserAuthToken userAuthToken = new UserAuthToken(user.get(),accessToken,date);
        userAuthTokenRepository.save(userAuthToken);
    }

    // This method is used to remove access token from the database. By remove, it means making the access token no longer viable
    @Override
    public void removeAccessToken(String accessToken) {
        userAuthTokenRepository.removeAuthToken(accessToken);
    }

    // This method is used to check whether the user is logged in or not
    @Override
    public UserAuthToken isUserLoggedIn(String accessToken) {
        return userAuthTokenRepository.isUserLoggedIn(accessToken);
    }

}
