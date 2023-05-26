package iss.nus.serverwatson.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import iss.nus.serverwatson.models.Bot;

@Repository
public class BotRepository {

    private final String SQL_BOT_ID_EXIST = 
    """
    SELECT EXISTS (SELECT * FROM bots WHERE id=?) AS 'exists';
    """;

    private final String SQL_BOT_SELECT_ALL = 
    """
    SELECT * FROM bots;
    """;

    private final String SQL_BOT_SELECT_BY_ID = 
    """
    SELECT * FROM bots WHERE id=?;
    """;

    private final String SQL_BOT_INSERT = 
    """
    INSERT INTO bots (id, username, image_url, token) VALUES (?, ?, ?, ?);
    """;

    private final String SQL_BOT_DELETE_BY_ID = 
    """
    DELETE FROM bots WHERE id=?;
    """;

    private final String SQL_BOT_SELECT_BY_USER_ID = 
    """
    SELECT * FROM bots WHERE id in 
        (SELECT bot_id FROM users_bots where user_id = ?);
    """;

    private final String SQL_USERS_BOTS_INSERT = 
    """
    INSERT INTO users_bots (user_id, bot_id) VALUES (?, ?);
    """;

    private final String SQL_USERS_BOTS_DELETE_BY_BOT_ID = 
    """
    DELETE FROM users_bots WHERE bot_id=?;
    """;

    @Autowired
    JdbcTemplate template;

    public Boolean botExists(Long id) {
        return template.queryForObject(SQL_BOT_ID_EXIST, Boolean.class, id);
    } 

    public List<Bot> findBots() {
        return template.query(SQL_BOT_SELECT_ALL, 
                            new BeanPropertyRowMapper<>(Bot.class));
    }

    public List<Bot> findBotsByUserId(Integer userId) {
        return template.query(SQL_BOT_SELECT_BY_USER_ID, 
                            new BeanPropertyRowMapper<>(Bot.class),
                            userId);
    }

    public Boolean insertBot(Bot bot) {
        // TODO: throws duplicate key exception
        return template.update(SQL_BOT_INSERT, 
                                bot.getId(), 
                                bot.getUsername(), 
                                bot.getImageUrl(), 
                                bot.getToken()
                                ) == 1;
    }

    public Boolean insertUserBotRelationship(Integer userId, Long botId) {
        return template.update(SQL_USERS_BOTS_INSERT, userId, botId) == 1;
    }

    public Boolean deleteBot(Long id) {
        return template.update(SQL_BOT_DELETE_BY_ID, id) == 1;
    }

    public Bot findBotById(Long id) {
        // TODO: throws EmptyResultDataAccessException
        return template.queryForObject(SQL_BOT_SELECT_BY_ID, 
                                        new BeanPropertyRowMapper<>(Bot.class), 
                                        id);
    }

    public Boolean deleteUserBotRelationshipByBotId(Long botId) {
        return template.update(SQL_USERS_BOTS_DELETE_BY_BOT_ID, botId) == 1;
    }

}
