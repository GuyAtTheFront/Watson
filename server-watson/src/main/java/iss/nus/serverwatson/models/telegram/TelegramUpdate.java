package iss.nus.serverwatson.models.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class TelegramUpdate {

    @JsonProperty("update_id")
    private Long updateId;
    private TelegramMessage message;

}
