package iss.nus.serverwatson.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.nus.serverwatson.models.Member;
import iss.nus.serverwatson.models.MemberDetail;
import iss.nus.serverwatson.models.Message;
import iss.nus.serverwatson.repositories.BotMembersRepository;
import iss.nus.serverwatson.repositories.MembersRepository;
import iss.nus.serverwatson.repositories.MessagesRepository;
import iss.nus.serverwatson.utils.Utils;

@Service
public class MemberDetailService {

    @Autowired
    BotMembersRepository botMemberRepo;

    @Autowired
    MembersRepository memberRepo;

    @Autowired
    MessagesRepository messagesRepo;

    public List<MemberDetail> getDetailsByBotId (Long botId) {
        
        // Get list of member_id from bot_member table
        List<Long> ids = botMemberRepo.findBotMemberByBotId(botId)
                            .stream()
                            .map(x -> x.getMemberId())
                            .toList();
        

        List<Message> messages = messagesRepo.findLatestMessagesByBotId(botId);

        List<Member> members = memberRepo.findMembers()
                                .stream()
                                .filter(member -> ids.contains(member.getId()))
                                .toList();

        List<MemberDetail> details = new LinkedList<>();

        for (long id: ids) {

            // get member from member table
            // get latest message from message table
            Optional<Member> optMember = members.stream().filter(m -> m.getId() == id).findFirst();
            Optional<Message> optMessage = messages.stream().filter(m -> m.getMemberId() == id).findFirst();


            // create MemberDetail from member and message
            if (optMember.isEmpty()) {
                // TODO: Throw Error!
                // This cannot be empty because id is FK to member PK
            }

            MemberDetail detail = new MemberDetail();
            detail.setId(id);
            detail.setUsername(optMember.get().getUsername());
            detail.setImageUrl(optMember.get().getImageUrl());

 
            if(optMessage.isEmpty()) {
                // This is okay, no previous stored message with bot    
                detail.setLastMessage(null);
                detail.setLastMessageTime(null);
            } else {
                detail.setLastMessage(optMessage.get().getMessageText());
                detail.setLastMessageTime(Utils.toLocalDateTime(optMessage.get().getDate()));
            }

            // add MemberDetail to list
            details.add(detail);
        }

        return details;
    }

    public Optional<MemberDetail> getDetailByIds(Long botId, Long memberId) {
        List<MemberDetail> details = this.getDetailsByBotId(botId);
        return details.stream().filter(x -> x.getId().equals(memberId)).findFirst();
    }

}
