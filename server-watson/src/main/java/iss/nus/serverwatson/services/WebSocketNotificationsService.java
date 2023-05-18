package iss.nus.serverwatson.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import iss.nus.serverwatson.models.ChatMessage;
import iss.nus.serverwatson.models.MemberDetail;
import iss.nus.serverwatson.models.Message;
import iss.nus.serverwatson.utils.MessageHelper;

@Service
public class WebSocketNotificationsService {
    
    private final String TOPIC_MEMBERS = "/topic/memberDetails/%s";
    private final String TOPIC_CHAT_MESSAGES = "/topic/chatMessages/%s/%s";

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private MemberDetailService memberDetailSvc;

    public void sendChatMember(Long botId, MemberDetail memberDetail) {
        template.convertAndSend(TOPIC_MEMBERS.formatted(botId), memberDetail);
    }

    public void sendChatMessage(Long botId, Long memberId, ChatMessage chatMessage) {
        template.convertAndSend(TOPIC_CHAT_MESSAGES.formatted(botId, memberId), chatMessage);
    }

    public void notifyNewMessage(Message message) {

        MemberDetail memberDetail = memberDetailSvc.getDetailByIds(message.getBotId(), message.getMemberId()).get();
        this.sendChatMember(message.getBotId(), memberDetail);

        ChatMessage incChatMessage = MessageHelper.toChatMessage(message);
        this.sendChatMessage(message.getBotId(), message.getMemberId(), incChatMessage);
    }
}