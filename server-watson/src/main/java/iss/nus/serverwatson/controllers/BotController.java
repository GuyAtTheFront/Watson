package iss.nus.serverwatson.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.nus.serverwatson.models.Bot;
import iss.nus.serverwatson.services.BotService;

@RestController
@RequestMapping(path="/api/bots", produces=MediaType.APPLICATION_JSON_VALUE)
public class BotController {

    @Autowired
    private BotService botSvc;

    @GetMapping(path="/{id}")
    public Bot getBotById(@PathVariable Long id) {

        Optional<Bot> opt = botSvc.findBotById(id);

        if (opt.isEmpty()) {
            // todo: throw exception?
            return null;
        }

        return opt.get();
    }

    @GetMapping()
    public List<Bot> getBots() {
        return botSvc.findBots();
    }

    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addBot(@RequestBody Bot bot) {

        // todo: throw exception if false?
        botSvc.addBot(bot);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteBot(@PathVariable Long id){

        // todo: throw exception if false?
        botSvc.deleteBot(id);

        return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
    }

}
