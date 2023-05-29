package project.mini.telegramwebhook.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TelegramWebhookService {

    // private final String METHOD_SET_WEBHOOK = "setWebhook";
    private final String METHOD_DELETE_WEBHOOK = "deleteWebhook";
    private final String METHOD_GET_WEBHOOK_INFO = "getWebhookInfo";
    
    // public void setWebhook(String token) {

    //     System.out.println("Setting webhook...");

    //     String id = token.split(":")[0];
    //     String endpoint = "/api/telegram/webhook/";


    //     // https://bcc9-138-75-37-204.ngrok-free.app/api/telegram/webhook/6127352122
    //     final String forwardingUrl = forwardingBaseUrl + endpoint + id;

    //     String uri = UriComponentsBuilder
    //         .fromUriString(formUrl(token, METHOD_SET_WEBHOOK))
    //         .queryParam("url", forwardingUrl)
    //         .build()
    //         .toUriString();

    //     RequestEntity<Void> request = RequestEntity
    //                     .get(uri)
    //                     .accept(MediaType.APPLICATION_JSON)
    //                     .build();

    //     RestTemplate template = new RestTemplate();

    //     ResponseEntity<String> response = template.exchange(request, String.class);

    //     System.out.println(response.getBody());
        
    //     return;
    // }

    public void deleteWebhook(String token) {

        System.out.println("deleting webhook...");

        String uri = UriComponentsBuilder
            .fromUriString(formUrl(token, METHOD_DELETE_WEBHOOK))
            .build()
            .toUriString();

        RequestEntity<Void> request = RequestEntity
                        .get(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> response = template.exchange(request, String.class);

        System.out.println(response.getBody());
        
        return;
    }

    public void getWebhookInfo(String token) {

        System.out.println("Getting webhook info...");

        String uri = UriComponentsBuilder
            .fromUriString(formUrl(token, METHOD_GET_WEBHOOK_INFO))
            .build()
            .toUriString();

        RequestEntity<Void> request = RequestEntity
                        .get(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> response = template.exchange(request, String.class);

        System.out.println(response.getBody());
        
        return;
    }

    private String formUrl(String token, String method) {
        return "https://api.telegram.org/bot%s/%s".formatted(token, method);
        
    }

}
