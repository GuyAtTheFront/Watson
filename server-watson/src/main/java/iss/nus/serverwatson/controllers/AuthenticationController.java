package iss.nus.serverwatson.controllers;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

record JwtRespose(String token) {}

@RestController
public class AuthenticationController {

    // @Autowired
    // private JwtEncoder jwtEncoder;

    // Authentication authentication
    // this is basically login
    @PostMapping("/authenticate") 
    public JwtRespose authenticate(@RequestBody String json) {
        System.out.println("controller --> " + json);
        // String scope = authentication.getAuthorities().stream()
        //                 .map(a -> a.getAuthority())
        //                 .collect(Collectors.joining(" "));

        // JwtClaimsSet claims = JwtClaimsSet.builder()
        //                 .issuer("self")
        //                 .issuedAt(Instant.now())
        //                 .expiresAt(Instant.now().plusSeconds(60 * 30))
        //                 .subject(authentication.getName())
        //                 .claim("scope", scope)
        //                 .build();

        // return new JwtRespose(jwtEncoder
        //                         .encode(JwtEncoderParameters.from(claims))
        //                         .getTokenValue());
        return null;
    }
}


