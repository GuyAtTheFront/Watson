package iss.nus.serverwatson.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iss.nus.serverwatson.models.User;
import iss.nus.serverwatson.services.UsersService;
import iss.nus.serverwatson.utils.Utils;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path="/api/users", produces=MediaType.APPLICATION_JSON_VALUE)
public class UsersController {
    
    @Autowired
    private UsersService userService;

    @GetMapping()
    public Boolean emailExists(@RequestParam String username) {
        return this.userService.userExists(username);
    }

    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerUser(@RequestBody String payload) {

        JsonObject json = Utils.toJson(payload);
        User user = new User(json.getString("username"), json.getString("password"));

        userService.insertUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
