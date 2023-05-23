package iss.nus.serverwatson.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@SuppressWarnings("deprecation")
@Configuration
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

}