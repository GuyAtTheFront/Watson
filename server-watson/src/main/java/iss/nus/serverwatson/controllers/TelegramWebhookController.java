package iss.nus.serverwatson.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import iss.nus.serverwatson.models.Bot;
import iss.nus.serverwatson.models.MemberDetail;
import iss.nus.serverwatson.models.Message;
import iss.nus.serverwatson.models.telegram.TelegramUpdate;
import iss.nus.serverwatson.services.BotService;
import iss.nus.serverwatson.services.MemberDetailService;
import iss.nus.serverwatson.services.MessageService;
import iss.nus.serverwatson.services.TelegramService;
import iss.nus.serverwatson.services.WebSocketNotificationsService;
import iss.nus.serverwatson.utils.MessageHelper;

@RestController
@RequestMapping("/api/telegram/router")
public class TelegramWebhookController {

    @Autowired
    MessageService messageSvc;

    @Autowired
    TelegramService telegramSvc;

    @Autowired
    BotService botSvc;

    @Autowired
    MemberDetailService memberDetailSvc;

    @Autowired
    WebSocketNotificationsService notificationSvc;
    
    @PostMapping(path="/{botId}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public void telegramWebHook(@PathVariable Long botId,@RequestBody String payload) {
        
        // Telegram webhook will post updates to this endpoint:
        //     (1) Save incoming updates to MongoDb
        //     (2) Notify incoming updates
        //     (2) Generate an appropriate response to the update
        //     (3) Send outgoing response to Telegram
        //     (4) Save outgoing response to MongoDb
        //     (6) Notify outgoing response
        
        // -------------------------------------
        // (1) Save incoming updates to MongoDb
        // -------------------------------------
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            TelegramUpdate update = objectMapper.readValue(payload, TelegramUpdate.class);
            System.out.println(update);

            Message incoming = MessageHelper.toIncomingMessage(update, botId);
            this.messageSvc.saveIncMessage(incoming);

        // ---------------------------------------------
        // (2) Send notifications for incoming updates
        // ---------------------------------------------

            notificationSvc.notifyNewMessage(incoming);

        // ----------------------------------------------------
        // (3) Generate an appropriate response to the update
        // ----------------------------------------------------
            // TODO: add bot response
            // String response = update.getMessage().getText();
            String response = "oi";

        // ----------------------------------------
        // (4) Send outgoing response to Telegram
        // ----------------------------------------

            telegramSvc.sendMessage(incoming.getMemberId(), botId, response);

        // ---------------------------------------
        // (5) Save outgoing response to MongoDb
        // ---------------------------------------
            Optional<Bot> opt = botSvc.findBotById(botId);
            if (opt.isEmpty()) {
                // TODO: throw error
            }
    
            Bot bot = opt.get();
    
            Message outgoing = MessageHelper.toOutgoingMessage(update, bot, response); 
            this.messageSvc.saveOutMessage(outgoing);

        // ---------------------------------------------
        // (6) Send notifications for incoming updates
        // ---------------------------------------------

            notificationSvc.notifyNewMessage(outgoing);
        
        } catch (Exception e) {
            System.out.println(e);
        }

        return;
    }

    // TODO: REMOVE
    @PostMapping("/test")
    public void test (@RequestBody MemberDetail detail) {
        System.out.println("testing... 1... 2... 3...");
        this.notificationSvc.sendChatMember(6127352122L, detail);
        // this.notificationSvc.sendChatMessage(6127352122L, 966363364L, msg);
    }
}
