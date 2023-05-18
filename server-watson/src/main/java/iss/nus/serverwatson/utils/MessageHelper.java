package iss.nus.serverwatson.utils;

import java.time.Instant;

import iss.nus.serverwatson.models.Bot;
import iss.nus.serverwatson.models.ChatMessage;
import iss.nus.serverwatson.models.Member;
import iss.nus.serverwatson.models.Message;
import iss.nus.serverwatson.models.telegram.TelegramUpdate;

public class MessageHelper {
    
    public static Member toMember(Message incoming) {
        Member member = new Member();
        member.setId(incoming.getMemberId());
        member.setUsername(incoming.getFromUsername());
        // TODO: update imageUrl
        member.setImageUrl("https://robohash.org/%d.png".formatted(member.getId()));
        return member;
    }

    public static Message toIncomingMessage(TelegramUpdate update, Long botId) {
        Message message = new Message();
        
        message.setMemberId(update.getMessage().getFrom().getId());
        message.setBotId(botId);

        message.setUpdateId(update.getUpdateId());
        message.setMessageId(update.getMessage().getMessageId());
        message.setFromId(update.getMessage().getFrom().getId());
        message.setFromUsername(update.getMessage().getFrom().getUsername());
        message.setToId(botId);
        message.setDate(update.getMessage().getDate());
        message.setMessageText(update.getMessage().getText());
        
        return message;
    }

    public static Message toOutgoingMessage(TelegramUpdate update, Bot bot, String response) {

        Message message = new Message();
        
        message.setMemberId(update.getMessage().getFrom().getId());
        message.setBotId(bot.getId());

        message.setUpdateId(update.getUpdateId());
        message.setMessageId(update.getMessage().getMessageId());
        message.setFromId(bot.getId());
        message.setFromUsername(bot.getUsername());
        message.setToId(update.getMessage().getFrom().getId());
        message.setDate(update.getMessage().getDate() + 1);
        message.setMessageText(response);

        return message;
    }

    public static Message toOutgoingMessage(Long memberId, Bot bot, String response) {
        Message message = new Message();
        
        message.setMemberId(memberId);
        message.setBotId(bot.getId());

        message.setUpdateId(-1L);
        message.setMessageId(-1L);
        message.setFromId(bot.getId());
        message.setFromUsername(bot.getUsername());
        message.setToId(memberId);
        message.setDate(Long.valueOf(Instant.now().toEpochMilli()/1000).intValue());
        message.setMessageText(response);

        return message;
    }

    public static ChatMessage toChatMessage(Message message) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setId(message.getMessageId());
        chatMessage.setFromId(message.getFromId());
        chatMessage.setToId(message.getToId());
        chatMessage.setContentType("text");
        chatMessage.setContent(message.getMessageText());
        chatMessage.setTimestamp(Utils.toLocalDateTime(message.getDate()));

        return chatMessage;
    }
}
