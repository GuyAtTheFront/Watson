package iss.nus.serverwatson.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class MemberDetail {
    
    private Long id;
    private String username;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private String ImageUrl;
}
