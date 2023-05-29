package project.mini.telegramwebhook.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RelayService {
    
    @Value("${routing.url}")
    String MY_URL;

    public void relay(String payload, String id) {

        String uri = UriComponentsBuilder
            .fromUriString(MY_URL+ "/" + id) 
            .build()
            .toUriString();

        RequestEntity<String> request = RequestEntity
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(payload);

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> response = template.exchange(request, String.class);

        System.out.println("relaying... " + response.getBody());
        
        return;
    }

}
