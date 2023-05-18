package iss.nus.serverwatson.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import iss.nus.serverwatson.models.Bot;
import jakarta.json.Json;

@Service
public class TelegramService {

    @Autowired
    BotService botSvc;
    
    private final String METHOD_SEND_MESSAGE = "sendMessage";

    public void sendMessage(Long chatId, Long botId, String response) {

        Optional<Bot> opt = botSvc.findBotById(botId);
        if (opt.isEmpty()) {
            // TODO: throw error
        }

        Bot bot = opt.get();

        String uri = UriComponentsBuilder.fromUriString(formUrl(bot.getToken(), METHOD_SEND_MESSAGE))
                    .build()
                    .toUriString();

        RequestEntity<String> request = RequestEntity
                        .post(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("content-type", MediaType.APPLICATION_JSON_VALUE)
                        .body(Json.createObjectBuilder()    
                                .add("chat_id", chatId)
                                .add("text", response)
                                .build().toString());


        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(request, String.class);

        // System.out.println("send status >>" + response.getStatusCode());
        // System.out.println("send body >>" + response.getBody());

        return;

    }
    
    private String formUrl(String token, String method) {
        return "https://api.telegram.org/bot%s/%s".formatted(token, method);
    }

}
