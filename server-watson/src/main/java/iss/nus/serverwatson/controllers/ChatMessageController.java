package iss.nus.serverwatson.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.nus.serverwatson.models.Bot;
import iss.nus.serverwatson.models.ChatMessage;
import iss.nus.serverwatson.models.Message;
import iss.nus.serverwatson.services.BotService;
import iss.nus.serverwatson.services.MessageService;
import iss.nus.serverwatson.services.TelegramService;
import iss.nus.serverwatson.services.WebSocketNotificationsService;
import iss.nus.serverwatson.utils.MessageHelper;
import iss.nus.serverwatson.utils.Utils;
import jakarta.json.JsonObject;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(path="/api/messages", produces=MediaType.APPLICATION_JSON_VALUE)
public class ChatMessageController {
    
    @Autowired
    MessageService messageSvc;

    @Autowired
    TelegramService telegramSvc;

    @Autowired
    BotService botSvc;

    @Autowired
    WebSocketNotificationsService notificationSvc;

    @GetMapping(path="/{botId}/{memberId}")
    public List<ChatMessage> getChatMessagesByIds(
            @PathVariable Long botId, @PathVariable Long memberId) {

        return messageSvc.findMessagesByIdsDescLimit(botId, memberId);
    }

    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendMessage(@RequestBody String payload) {
        
        JsonObject json = Utils.toJson(payload);
        Long memberId = json.getJsonNumber("memberId").longValue();
        Long botId = json.getJsonNumber("botId").longValue();
        String message = json.getString("message");


        telegramSvc.sendMessage(memberId, botId, message);

        Optional<Bot> opt = botSvc.findBotById(botId);
        if (opt.isEmpty()) {
            // TODO: throw error
        }

        Bot bot = opt.get();

        Message outgoing = MessageHelper.toOutgoingMessage(memberId, bot, message); 
        this.messageSvc.saveOutMessage(outgoing);

        notificationSvc.notifyNewMessage(outgoing);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
