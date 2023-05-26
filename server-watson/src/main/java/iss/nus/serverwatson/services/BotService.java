package iss.nus.serverwatson.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iss.nus.serverwatson.models.Bot;
import iss.nus.serverwatson.repositories.BotMembersRepository;
import iss.nus.serverwatson.repositories.BotRepository;

@Service
public class BotService {
    
    @Autowired
    BotRepository botRepo;

    @Autowired
    BotMembersRepository botMemberRepo;

    public List<Bot> findBots() {
        return botRepo.findBots();
    }    

    public List<Bot> findBotsByUserId(Integer UserId) {
        return botRepo.findBotsByUserId(UserId);
    }
    
    // TODO: TRANSACTIONAL?
    public void addBot(Integer userId, Bot bot) {
        if(botRepo.botExists(bot.getId())) {
            return;
        }

        botRepo.insertBot(bot);
        botRepo.insertUserBotRelationship(userId, bot.getId());
    }

    //TODO TRANSACTIONAL?
    public Boolean deleteBot(Long id) {
        if(!botRepo.botExists(id)) {
            return false;
        }

        // delete FK
        System.out.println("userbot");
        botRepo.deleteUserBotRelationshipByBotId(id);

        System.out.println("botmembers");
        botMemberRepo.deleteBotMemberByBotId(id);

        // delete bot
        System.out.println("bot");
        botRepo.deleteBot(id);
        return true;
    }

    public Optional<Bot> findBotById(Long id) {
        if(!botRepo.botExists(id)) {
            return Optional.empty();
        }

        return Optional.of(botRepo.findBotById(id));
    }

}
