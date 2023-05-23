package iss.nus.serverwatson.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPopupMenu.Separator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.json.JsonObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @Component
public class JwtFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    public JwtFilter() {
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/**", null));
    }

    @Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
        System.out.println("hello");

        // request.getReader().lines().collect(new Separator(" "))

        String username = request.getParameter("username");
        username = (username != null) ? username.trim() : "";

        String password = request.getParameter("password");
        password = (password != null) ? password : "";

        System.out.println("username -->" + username);
        System.out.println("password -->" + password);


        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
        password);

        this.getAuthenticationManager().authenticate(authRequest);

        System.out.println(authRequest);

        return super.attemptAuthentication(request, response);
    }
    // public JwtRequestFilter() {
	// 	super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
	// }

	// public JwtRequestFilter(AuthenticationManager authenticationManager) {
	// 	super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
	// }

    // @Autowired
    // @Override
    // public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
    //     super.setAuthenticationFailureHandler(failureHandler);
    // }

    // @Autowired
    // @Override
    // public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
    //     super.setAuthenticationSuccessHandler(successHandler);
    // }
     

    // @Override
    // public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    //     throws AuthenticationException 
    //     {

    //     

    //     // UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    //     //     if(userDetails == null || userDetails.getPassword() != AuthHelper.SecuredPasswordGenerator(password)) {
    //     //         throw new BadCredentialsException("Invalid username/password");
    //     //     }

    //     // UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
    //     //                                         username, password, userDetails.getAuthorities());
                                                
    //     return this.getAuthenticationManager().authenticate(authRequest);

    // }
    
}
