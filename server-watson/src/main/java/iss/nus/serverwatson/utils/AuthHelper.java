package iss.nus.serverwatson.utils;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import iss.nus.serverwatson.models.JwtResponse;


public class AuthHelper {

    public static String securedPasswordGenerator (String password) {
 
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encodedPassword = encoder.encode(password);
         
    return encodedPassword;
    }

    public static JwtResponse generateJwtResponse (Authentication authentication, JwtEncoder encoder) {
        
        Instant now = Instant.now();
        Integer expiresIn = 60 * 60 * 12;
        Instant expiresAt = Instant.now().plusSeconds(expiresIn);
        String username = authentication.getName();

        String scope = authentication.getAuthorities().stream()
                        .map(a -> a.getAuthority())
                        .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                        .issuer("self")
                        .issuedAt(now)
                        .expiresAt(expiresAt)
                        .subject(username)
                        .claim("scope", scope)
                        .build();

        String token = encoder.encode(JwtEncoderParameters.from(claims))
                                .getTokenValue();

        return new JwtResponse(username, token, ""+(expiresAt.toEpochMilli()/1000));

    }

    public static String generateJwtToken (Authentication authentication, JwtEncoder encoder) {

        String scope = authentication.getAuthorities().stream()
        .map(a -> a.getAuthority())
        .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60*60*12))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims))
                        .getTokenValue();
    }
}
