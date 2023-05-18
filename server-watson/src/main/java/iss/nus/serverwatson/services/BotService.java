package iss.nus.serverwatson.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.nus.serverwatson.models.Bot;
import iss.nus.serverwatson.repositories.BotRepository;

@Service
public class BotService {
    
    @Autowired
    BotRepository botRepo;

    public List<Bot> findBots() {
        return botRepo.findBots();
    }
    
    public Boolean addBot(Bot bot) {
        if(botRepo.botExists(bot.getId())) {
            return false;
        }

        return botRepo.insertBot(bot);
    }

    public Boolean deleteBot(Long id) {
        if(!botRepo.botExists(id)) {
            return false;
        }

        return botRepo.deleteBot(id);
    }

    public Optional<Bot> findBotById(Long id) {
        if(!botRepo.botExists(id)) {
            return Optional.empty();
        }

        return Optional.of(botRepo.findBotById(id));
    }

}
