package iss.nus.serverwatson.dev;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // @Autowired
    // AuthenticationManager authenticationManager;

    // @Autowired
    // JwtDecoder jwtDecoder;

    private JwtDecoder jwtDecoder;
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(JwtDecoder jwtDecoder, AuthenticationManager authenticationManager) {
        this.jwtDecoder = jwtDecoder;
        this.authenticationManager = authenticationManager;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                String authHeader = request.getHeader("Authorization");

                if(null == authHeader || !authHeader.startsWith("Bearer ")) {
                    filterChain.doFilter(request, response);
                }

                String token = Arrays.asList(authHeader.split(" ")).get(1);
                
                Jwt jwt = jwtDecoder.decode(token);
                
                System.out.println(jwt.toString());

                filterChain.doFilter(request, response);

    }
    
}
