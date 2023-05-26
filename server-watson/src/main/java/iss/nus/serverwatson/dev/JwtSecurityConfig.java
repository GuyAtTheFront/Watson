package iss.nus.serverwatson.dev;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

// @Configuration
public class JwtSecurityConfig {

    @Autowired
    private DataSource dataSource;

	// @Autowired
	// JwtAuthenticationFilter jwtAuthenticationFilter;

	

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception  {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from users where username=?")
            .authoritiesByUsernameQuery("select username, role from users where username=?")
        ;
    }

    @Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// http.addFilterAt(jwtAuthenticationFilter ,BearerTokenAuthenticationFilter.class);

		http.authorizeHttpRequests(
						auth -> {
                                // auth.requestMatchers("/dashboard/**").authenticated();
                                auth
                                    .requestMatchers(HttpMethod.GET, "/api/bots").authenticated()
                                    .anyRequest().permitAll();
                                    // .anyRequest().denyAll();

						});
		
		http.sessionManagement(
						session -> 
							session.sessionCreationPolicy(
									SessionCreationPolicy.STATELESS
                                    )
						);
		
		http.httpBasic();
		
		http.csrf().disable();
		
		http.headers().frameOptions().sameOrigin();

		http.oauth2ResourceServer().jwt();
				
		return http.build();
	}

    // @Bean
	// SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	// 	http.authorizeHttpRequests(
	// 					auth -> {
    //                         try {
    //                             auth.anyRequest().permitAll();
    //                             // auth.anyRequest().authenticated()
    //                             // .and()
    //                             // .addFilterBefore(jwtRequestFilter(authenticationManager(http)), UsernamePasswordAuthenticationFilter.class);
    //                         } catch (Exception e) {
    //                             // TODO Auto-generated catch block
    //                             e.printStackTrace();
    //                         }
    //                         // .addFilterBefore(null, UsernamePasswordAuthenticationFilter.class);
    //                         // auth.anyRequest().permitAll();
    //                         // auth
    //                         // .requestMatchers(
    //                         //     "/index",
    //                         //     "",
    //                         //     "/login",
    //                         //     "/logout",
    //                         //     "/http://localhost:8082/api/bots/6127352122"
    //                         //     ).permitAll()
    //                         // .anyRequest().authenticated();
	// 					});
		
	// 	http.sessionManagement(
	// 					session -> 
	// 						session.sessionCreationPolicy(
	// 								SessionCreationPolicy.STATELESS)
	// 					);
		
	// 	http.httpBasic().disable();
		
	// 	http.csrf().disable();
		
	// 	http.headers().frameOptions().sameOrigin();
		
	// 	http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
		
	// 	return http.build();
	// }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user = User.withDefaultPasswordEncoder()
    //         .username("user")
    //         .password("password")
    //         .roles("USER")
    //         .build();
    //     return new InMemoryUserDetailsManager(user);
    // }
	
	// @Bean
	// public BCryptPasswordEncoder passwordEncoder() {
	// 	return new BCryptPasswordEncoder();
	// }
	
	@Bean
	public KeyPair keyPair() {
		try {
			var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			return keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Bean
	public RSAKey rsaKey(KeyPair keyPair) {
		
		return new RSAKey
				.Builder((RSAPublicKey)keyPair.getPublic())
				.privateKey(keyPair.getPrivate())
				.keyID(UUID.randomUUID().toString())
				.build();
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
		var jwkSet = new JWKSet(rsaKey);
		
		return (jwkSelector, context) ->  jwkSelector.select(jwkSet);
		
	}
	
	@Bean
	public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
		return NimbusJwtDecoder
				.withPublicKey(rsaKey.toRSAPublicKey())
				.build();
		
	}
	
	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}

    // @Bean
    // @Autowired
    // public JwtFilter jwtRequestFilter(AuthenticationManager authenticationManager) {
    //     JwtFilter filter = new JwtFilter();
    //     filter.setAuthenticationManager(authenticationManager);
    //     // filter.setAuthenticationFailureHandler(null);
    //     return filter;
    // }

	
}
