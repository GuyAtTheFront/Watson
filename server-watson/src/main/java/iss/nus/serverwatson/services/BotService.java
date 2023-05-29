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
    
    @Transactional
    public void addBot(Integer userId, Bot bot) {

        try {

            if(!botRepo.botExists(bot.getId())) {
                botRepo.insertBot(bot);
            }

            botRepo.insertUserBotRelationship(userId, bot.getId());

        } catch (Exception e) {
            throw new RuntimeException("Failed to add bot");
        }
    }

    @Transactional
    public Boolean deleteBot(Long id) {

        try {
            if(!botRepo.botExists(id)) {
                return false;
            }

            // delete FK
            botRepo.deleteUserBotRelationshipByBotId(id);

            botMemberRepo.deleteBotMemberByBotId(id);

            // delete bot
            botRepo.deleteBot(id);
        } catch (Exception e) {
            throw new RuntimeException("failed to delete bot");
        }
        return true;
    }

    public Optional<Bot> findBotById(Long id) {
        if(!botRepo.botExists(id)) {
            return Optional.empty();
        }

        return Optional.of(botRepo.findBotById(id));
    }

}
