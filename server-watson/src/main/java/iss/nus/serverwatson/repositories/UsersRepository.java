package iss.nus.serverwatson.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import iss.nus.serverwatson.models.User;

@Repository
public class UsersRepository {
    
    private final String SQL_USERS_FIND_BY_USERNAME = 
    """
    SELECT * FROM users WHERE username=?;
    """;

    private final String SQL_USERS_INSERT = 
    """
    INSERT INTO users (username, password, role, enabled)
        VALUES (?, ?, ?, ?);
    """;

    @Autowired
    private JdbcTemplate template;

    public Optional<User> findUserByUsername(String username) {
        try {
        User user = template.queryForObject(SQL_USERS_FIND_BY_USERNAME, new BeanPropertyRowMapper<>(User.class), username);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Boolean insertUser(User user) {
        Integer inserted = template.update(SQL_USERS_INSERT, user.getUsername(), user.getPassword(), user.getRole(), user.getEnabled());
        return (inserted == 1) ? true : false;
    }

}
