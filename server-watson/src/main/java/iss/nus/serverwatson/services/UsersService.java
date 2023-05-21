package iss.nus.serverwatson.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.nus.serverwatson.models.User;
import iss.nus.serverwatson.repositories.UsersRepository;

@Service
public class UsersService {
    
    @Autowired
    UsersRepository usersRepository;

    public Boolean userExists(String username){
        Optional<User> opt = usersRepository.findUserByUsername(username);
        return (opt.isEmpty()) ? false : true;
    }

    public void insertUser(User user) {
        Boolean inserted = usersRepository.insertUser(user);

        if (!inserted) {
            //TODO: throw error
        }

        return;
    }

}
