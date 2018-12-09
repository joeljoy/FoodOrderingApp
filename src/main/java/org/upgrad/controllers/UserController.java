package org.upgrad.controllers;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.User;
import org.upgrad.models.UserAuthToken;
import org.upgrad.services.UserAuthTokenService;
import org.upgrad.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthTokenService userAuthTokenService;

    @PostMapping("/signup")
    @CrossOrigin
    public ResponseEntity<?> signup(@RequestParam(value = "First Name") String FirstName, @RequestParam(value = "Last Name",required = false) String Lastname, @RequestParam(value = "Email") String email, @RequestParam(value = "Contact number") String contactno, @RequestParam(value = "Password") String password){
        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern emailpat = Pattern.compile(emailRegex);
        boolean emailmatched = emailpat.matcher(email).matches();
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(contactno);
        boolean validcontactno = m.find() && m.group().equals(contactno);
        boolean validpassword = false;
        if(password.length()>=8){
            boolean hasUpperCase=false;
            boolean hasSpecialChar=false;
            boolean hasDigit = false;
            for(int i =0;i<password.length();i++) {
                char x = password.charAt(i);
                if(Character.isUpperCase(x))
                    hasUpperCase=true;
                else if (Character.isDigit(x))
                    hasDigit = true;
                else if(x == '#'||x == '@'||x=='$'||x=='%'||x=='&'||x=='*'||x=='!'||x=='^')
                    hasSpecialChar = true ;

            }
            if(hasDigit && hasSpecialChar && hasUpperCase){
                validpassword = true;
            }

        }

        if(!validcontactno){
            return new ResponseEntity<>("Invalid contact number!",HttpStatus.OK);
        }
        else if(!validpassword){
            return new ResponseEntity<>("Weak password!",HttpStatus.OK);
        }
        else if(!emailmatched){
            return new ResponseEntity<>("Invalid email-id format!",HttpStatus.OK);
        }
        else if(userService.findUser(contactno) != null){
            return new ResponseEntity<>("Try any other contact number, this contact number has already been registered!", HttpStatus.OK);
        }
        else{
            User newuser = new User(FirstName,Lastname,email,contactno,sha256hex);
            userService.addUser(newuser);

            return new  ResponseEntity<>("User with contact number "+contactno +" successfully registered!", HttpStatus.CREATED);
        }

    }


    /*
    * This endpoint is used to login a user.
    * Here contact number and password has to be provided to match the credentials.
    */
    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<?> login(@RequestParam String contactNumber, @RequestParam String password){
        String passwordByUser = String.valueOf(userService.findUserPassword(contactNumber));
        String sha256hex = Hashing.sha256()
                .hashString(password, Charsets.US_ASCII)
                .toString();
        if(userService.findUserPassword(contactNumber)==null) return new ResponseEntity<>("This contact number has not been registered!",HttpStatus.OK);
        else if (!(passwordByUser.equalsIgnoreCase(sha256hex))) {
            return new ResponseEntity<>("Invalid Credentials",HttpStatus.UNAUTHORIZED);
        }
        else{
            User user = userService.findUser(contactNumber);
            String accessToken = UUID.randomUUID().toString();
            userAuthTokenService.addAccessToken(user.getId(),accessToken);
            HttpHeaders headers = new HttpHeaders();
            headers.add("access-token", accessToken);
            List<String> header = new ArrayList<>();
            header.add("access-token");
            headers.setAccessControlExposeHeaders(header);
            return new ResponseEntity<>(user,headers,HttpStatus.OK);
        }
    }

    /*
    * This endpoint is used to logout a user.
    * Authentication is required to access this endpoint, so accessToken is taken as request header to make sure user is authenticated.
    */
    @PutMapping("/logout")
    @CrossOrigin
    public ResponseEntity<String> logout(@RequestHeader String accessToken){
        if(userAuthTokenService.isUserLoggedIn(accessToken) == null){
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else if(userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt()!=null){
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }  else{
            userAuthTokenService.removeAccessToken(accessToken);
            return new ResponseEntity<>("You have logged out successfully!",HttpStatus.OK);}
    }


    @PutMapping("/user")
    @CrossOrigin
    public ResponseEntity<String> updateuser(@RequestHeader String accessToken, @RequestParam(value = "First Name") String firstname, @RequestParam(value = "Last Name", required = false) String lastname){
        UserAuthToken tokendetails = userAuthTokenService.isUserLoggedIn(accessToken);
//        System.out.println(tokendetails.getAccessToken());
        if(tokendetails == null){
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        else if(userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt()!=null){
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }  else{
           User user = tokendetails.getUser();
           userService.updateUserDetails(firstname,lastname,user);
           User updated = userService.findUserById(user.getId());
//           User user = userService.findUser("9090909");
           return new ResponseEntity(updated, HttpStatus.OK);
        }

    }


    @PutMapping("/user/password")
    @CrossOrigin
    public ResponseEntity<String> changepassword(@RequestHeader String accessToken,@RequestParam String oldPassword, @RequestParam String newPassword) {
        boolean validpassword = false;
        if(newPassword.length()>=8){
            boolean hasUpperCase=false;
            boolean hasSpecialChar=false;
            boolean hasDigit = false;
            for(int i =0;i<newPassword.length();i++) {
                char x = newPassword.charAt(i);
                if(Character.isUpperCase(x))
                    hasUpperCase=true;
                else if (Character.isDigit(x))
                    hasDigit = true;
                else if(x == '#'||x == '@'||x=='$'||x=='%'||x=='&'||x=='*'||x=='!'||x=='^')
                    hasSpecialChar = true ;

            }
            if(hasDigit && hasSpecialChar && hasUpperCase){
                validpassword = true;
            }

        }
        boolean updated = false;
        Integer userId = userAuthTokenService.getUserId(accessToken);

        String newPasswordsha = Hashing.sha256()
                .hashString(newPassword, Charsets.US_ASCII)
                .toString();

        UserAuthToken tokendetails = userAuthTokenService.isUserLoggedIn(accessToken);
        User user = tokendetails.getUser();
        String OldpasswordSetByUser = user.getPassword();
        String oldpasswordsha = Hashing.sha256()
                .hashString(oldPassword, Charsets.US_ASCII)
                .toString();

        if (tokendetails == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (tokendetails.getLogoutAt() != null) {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if(!validpassword){
            return new ResponseEntity<>("Weak password!", HttpStatus.BAD_REQUEST);
        }
        else
        {
            if(!(OldpasswordSetByUser.equalsIgnoreCase(oldpasswordsha))){
                return new ResponseEntity<>("Your password did not match to your old password!", HttpStatus.UNAUTHORIZED);
            }
            userService.updateUserPassword(newPasswordsha,user.getId());


            return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
        }

    }
}
