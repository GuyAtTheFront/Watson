package iss.nus.serverwatson.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import iss.nus.serverwatson.models.BotMember;

@Repository
public class BotMembersRepository {
    
    private final String SQL_BOT_MEMBER_EXIST = 
    """
    SELECT EXISTS (SELECT * FROM bot_member WHERE bot_id=? AND member_id=?) AS 'exists';
    """;

    private final String SQL_BOT_MEMBER_SELECT_BY_BOT_ID = 
    """
    SELECT * FROM bot_member WHERE bot_id=?;
    """;

    private final String SQL_BOT_MEMBER_INSERT = 
    """
    INSERT INTO bot_member (bot_id, member_id) VALUES (?, ?);
    """;

    private final String SQL_BOT_MEMBER_DELETE_BY_IDS = 
    """
    DELETE FROM bot_member WHERE bot_id=? and member_id=?;
    """;

    @Autowired
    JdbcTemplate template;

    public Boolean botMemberExists(Long botId, Long memberId) {
        return template.queryForObject(SQL_BOT_MEMBER_EXIST, Boolean.class, botId, memberId);
    } 

    public List<BotMember> findBotMemberByBotId(Long botId) {
        return template.query(SQL_BOT_MEMBER_SELECT_BY_BOT_ID, 
                            new BeanPropertyRowMapper<>(BotMember.class), 
                            botId);
    }

    public Boolean insertBotMember(BotMember botMember) {
        // TODO: throws duplicate key exception
        return template.update(SQL_BOT_MEMBER_INSERT, 
                                botMember.getBotId(),
                                botMember.getMemberId()
                                ) == 1;
    }

    public Boolean deleteBotMemberByIds(Long botId, Long memberId ) {
        return template.update(SQL_BOT_MEMBER_DELETE_BY_IDS, botId, memberId) == 1;
    }

}
