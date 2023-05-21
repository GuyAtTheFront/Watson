package iss.nus.serverwatson.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthHelper {
 
    public static String SecuredPasswordGenerator (String password) {
 
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encodedPassword = encoder.encode(password);
         
    return encodedPassword;
    }


}
