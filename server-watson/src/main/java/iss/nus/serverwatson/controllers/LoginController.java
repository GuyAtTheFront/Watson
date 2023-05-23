package iss.nus.serverwatson.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.nus.serverwatson.services.UsersService;
import iss.nus.serverwatson.utils.Utils;
import jakarta.json.Json;
import jakarta.json.JsonObject;

record LoginCredential(String username, String password) {}

@RestController
@RequestMapping(path="/api/login", produces=MediaType.APPLICATION_JSON_VALUE)
public class LoginController {
    
    @Autowired
    UsersService userSvc;

    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody String payload) {
        
        JsonObject json = Utils.toJson(payload);
        LoginCredential login = new LoginCredential(json.getString("username"), 
                                                    json.getString("password"));

        String jwt = userSvc.authenticateUser(login.username(), login.password());

        return Json.createObjectBuilder()
                    .add("token", jwt)
                    .build().toString();
    }
}
