package iss.nus.serverwatson.models.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class TelegramMessage {

    @JsonProperty("message_id")
    private Long messageId;
    private TelegramFrom from;
    private TelegramChat chat;
    private Integer date;
    private String text;


}