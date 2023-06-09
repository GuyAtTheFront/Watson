package iss.nus.serverwatson.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

import iss.nus.serverwatson.models.User;
import iss.nus.serverwatson.repositories.UsersRepository;
import iss.nus.serverwatson.utils.AuthHelper;

@Service
public class UsersService {
    
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtEncoder jwtEncoder;

    public Boolean userExists(String username){
        Optional<User> opt = usersRepository.findUserByUsername(username);
        return (opt.isEmpty()) ? false : true;
    }

    public Optional<User> getUserByUsername(String username) {
        return usersRepository.findUserByUsername(username);
    }

    public void insertUser(User user) {
        Boolean inserted = usersRepository.insertUser(user);

        if (!inserted) {
            //TODO: throw error
        }
        return;
    }

    public String authenticateUser(String username, String password) {

        UsernamePasswordAuthenticationToken authRequest = 
            UsernamePasswordAuthenticationToken.unauthenticated(username,password);

        Authentication authentication = authenticationManager.authenticate(authRequest);

        if(!authentication.isAuthenticated()) {
            // not authenticated    
        } 

        User user = usersRepository.findUserByUsername(username).get();
        // String userToken = AuthHelper.generateJwtToken(authentication, jwtEncoder);
        
        return AuthHelper.generateJwtToken(authentication, user, jwtEncoder);

    }
}
