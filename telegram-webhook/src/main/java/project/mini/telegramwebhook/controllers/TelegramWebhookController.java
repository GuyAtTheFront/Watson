package project.mini.telegramwebhook.controllers;

import org.springframework.web.bind.annotation.RestController;

import project.mini.telegramwebhook.services.RelayService;
import project.mini.telegramwebhook.services.TelegramWebhookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/telegram/webhook")
public class TelegramWebhookController {

    @Autowired
    TelegramWebhookService webhookService;

    @Autowired
    RelayService relayService;

    // @GetMapping("/enable/token")
    // public void enableWebhook(@PathVariable String token) {
    //     webhookService.setWebhook(token);
    //     return;
    // }   

    @GetMapping("/disable/token")
    public void disableWebhook(@PathVariable String token) {
        webhookService.deleteWebhook(token);
        return;
    }
    
    @GetMapping("/info/token")
    public void getWebhookInfo(@PathVariable String token) {
        webhookService.getWebhookInfo(token);
        return;
    }

    @PostMapping(path="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public void telegramWebHook(@PathVariable String id, @RequestBody String payload) {
        System.out.println(payload);
        relayService.relay(payload, id);
        return;
    }
}
