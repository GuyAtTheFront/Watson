package iss.nus.serverwatson.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import iss.nus.serverwatson.models.Feedback;
import iss.nus.serverwatson.services.EmailService;

@RestController
public class EmailController {
    
    @Autowired
    EmailService emailService;

    @PostMapping(path="/api/feedback", consumes=MediaType.APPLICATION_JSON_VALUE)
    public void feedback(@RequestBody Feedback feedback) {
        try {
            this.emailService.sendEmail(feedback);
        } catch (Exception e) {
            // TODO: Error
            System.out.println("too bad...");
        }
    }

}
