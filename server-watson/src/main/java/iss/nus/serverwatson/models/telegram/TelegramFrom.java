package iss.nus.serverwatson.models.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class TelegramFrom {
    private Long id;

    @JsonProperty("is_bot")
    private Boolean isBot;
    
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;
    private String username;

    @JsonProperty("language_code")
    private String languageCode;
}
