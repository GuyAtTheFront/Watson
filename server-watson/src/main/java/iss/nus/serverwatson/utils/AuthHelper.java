package iss.nus.serverwatson.utils;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import iss.nus.serverwatson.models.JwtResponse;
import iss.nus.serverwatson.models.User;


public class AuthHelper {

    public static String securedPasswordGenerator (String password) {
 
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encodedPassword = encoder.encode(password);
         
    return encodedPassword;
    }

    public static String generateJwtToken (Authentication authentication, User user, JwtEncoder encoder) {

        String scope = authentication.getAuthorities().stream()
        .map(a -> a.getAuthority())
        .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60*60*12))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("username", user.getUsername())
                .claim("userId", user.getUserId())
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims))
                        .getTokenValue();
    }
}
