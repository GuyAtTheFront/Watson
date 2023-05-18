package iss.nus.serverwatson.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class Message {

    private Long memberId;
    private Long botId;
    private Long updateId;
    private Long messageId;
    private Long fromId;
    private String fromUsername; 
    private Long toId;
    private Integer date;
    private String messageText;

}   
