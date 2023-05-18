package iss.nus.serverwatson.services;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import iss.nus.serverwatson.models.BotMember;
import iss.nus.serverwatson.models.ChatMessage;
import iss.nus.serverwatson.models.Message;
import iss.nus.serverwatson.repositories.BotMembersRepository;
import iss.nus.serverwatson.repositories.MembersRepository;
import iss.nus.serverwatson.repositories.MessagesRepository;
import iss.nus.serverwatson.utils.MessageHelper;

@Service
public class MessageService {
    
    @Autowired
    MembersRepository memberRepo;

    @Autowired
    BotMembersRepository botMembersRepo;

    @Autowired
    MessagesRepository messagesRepo;

    @Autowired
    BotService botSvc;
    
    public void saveIncMessage(Message incoming){

        final Long botId = incoming.getBotId();
        final Long memberId = incoming.getMemberId();

        // Creates database entries if:
        //     (1) Member does not exist, and/or
        //     (2) bot_member does not exist
        // Then:
        //     Inserts incoming message to MongoDb

        if(!botMembersRepo.botMemberExists(botId, memberId)) {
            if(!memberRepo.memberExists(memberId)) {
                memberRepo.insertMember(MessageHelper.toMember(incoming));
            }

            BotMember botMembers = new BotMember(botId, memberId);
            
            botMembersRepo.insertBotMember(botMembers);
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(incoming);
            Document doc = Document.parse(json);

            messagesRepo.insertMessage(doc);

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveOutMessage(Message outgoing) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(outgoing);
            Document doc = Document.parse(json);

            messagesRepo.insertMessage(doc);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ChatMessage> findMessagesByIdsDescLimit(Long botId, Long memberId) {
        
        // Defaults to skip=0, limit=50
        List<Message> messages = messagesRepo.findMessagesByIdsDescLimit(botId, memberId);        
        List<ChatMessage> chatMessages = messages.stream()
                            .map(msg -> MessageHelper.toChatMessage(msg))
                            .toList();
        
        return chatMessages;
    }

}
