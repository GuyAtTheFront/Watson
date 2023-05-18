package iss.nus.serverwatson.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class ChatMessage {
    
    private Long id;
    private Long fromId;
    private Long toId;
    private String content;
    private String contentType;
    private LocalDateTime timestamp;

}
