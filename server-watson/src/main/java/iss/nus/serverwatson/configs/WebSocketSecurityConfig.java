package iss.nus.serverwatson.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import jakarta.annotation.security.DenyAll;

@Configuration
// @EnableWebSocketSecurity
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer{

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

    @Override 
    protected void configureInbound(
    MessageSecurityMetadataSourceRegistry messages) { 
        messages
        // .simpDestMatchers("/secured/**").authenticated()
        .anyMessage().permitAll(); 
    }

    // @Bean
    // AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
    //     messages
    //         // .nullDestMatcher().authenticated() 
    //         .anyMessage().permitAll();
    //         // .simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.SUBSCRIBE, SimpMessageType.HEARTBEAT).permitAll();
    //         // .anyMessage().permitAll();
    //         // .simpSubscribeDestMatchers("/topic/**").permitAll() 
    //         // .simpDestMatchers("/app/**").permitAll()//.hasRole("USER") 
    //         // .simpSubscribeDestMatchers("/user/**", "/topic/friends/*").hasRole("USER") 
    //         // .simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll() 
    //         // .anyMessage().denyAll(); 

    //     return messages.build();
    // }

    // @Bean
	// SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	// 	http.authorizeHttpRequests(
	// 					auth -> {
    //                             auth.anyRequest().permitAll();
	// 					});
		
	// 	http.sessionManagement(
	// 					session -> 
	// 						session.sessionCreationPolicy(
	// 								SessionCreationPolicy.STATELESS)
	// 					);
		
	// 	http.httpBasic().disable();
		
	// 	http.csrf().disable();
		
	// 	http.headers().frameOptions().sameOrigin();
				
	// 	return http.build();
	// }

}