package iss.nus.serverwatson.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import iss.nus.serverwatson.models.Member;

@Repository
public class MembersRepository {

    private final String SQL_MEMBERS_ID_EXIST = 
    """
    SELECT EXISTS (SELECT * FROM members WHERE id=?) as 'exists';
    """;

    private final String SQL_MEMBERS_SELECT_ALL = 
    """
    SELECT * FROM members;
    """;

    private final String SQL_MEMBERS_SELECT_BY_ID = 
    """
    SELECT * FROM members WHERE id=?;
    """;

    private final String SQL_MEMBERS_INSERT = 
    """
    INSERT INTO members (id, username, image_url) VALUES (?, ?, ?);
    """;

    private final String SQL_MEMBERS_DELETE_BY_ID = 
    """
    DELETE FROM members where id=?;
    """;

    @Autowired
    JdbcTemplate template;

    public Boolean memberExists(Long id) {
        return template.queryForObject(SQL_MEMBERS_ID_EXIST, Boolean.class, id);
    } 

    public List<Member> findMembers() {
        return template.query(SQL_MEMBERS_SELECT_ALL, 
                            new BeanPropertyRowMapper<>(Member.class));
    }

    public Boolean insertMember(Member member) {
        // TODO: throws duplicate key exception
        return template.update(SQL_MEMBERS_INSERT, 
                                member.getId(), 
                                member.getUsername(), 
                                member.getImageUrl()
                                ) == 1;
    }

    public Boolean deleteMember(Long id) {
        return template.update(SQL_MEMBERS_DELETE_BY_ID, id) == 1;
    }

    public Member findMemberById(Long id) {
        // TODO: throws EmptyResultDataAccessException
        return template.queryForObject(SQL_MEMBERS_SELECT_BY_ID, 
                                        new BeanPropertyRowMapper<>(Member.class), 
                                        id);
    }

}
