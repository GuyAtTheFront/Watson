package iss.nus.serverwatson.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class BotMember {
    
    private Long botId;
    private Long memberId; 

    public BotMember(Long botId, Long memberId){
        this.botId = botId;
        this.memberId = memberId;
    }
}
