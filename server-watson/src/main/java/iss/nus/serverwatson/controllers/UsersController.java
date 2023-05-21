package iss.nus.serverwatson.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/users", consumes=MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    
    @GetMapping()
    public Boolean emailExists(@RequestParam String email) {


        return true;
    }

    // create user

}
