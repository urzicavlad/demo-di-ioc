package ro.softvision.data.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ro.softvision.data.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("userTemplateRepo")
public class JdbcTemplateUserRepo implements UserRepo{

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<User> rowMapper = new UserRowMapper();

    public JdbcTemplateUserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT * FROM P_USER";
        jdbcTemplate.queryForList(sql);
        return null;
    }

    /*
    * ROW MAPPER
    * */
    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("ID");
            String email = rs.getString("EMAIL");
            String username = rs.getString("USERNAME");
            String password = rs.getString("PASSWORD");
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            return user;
        }
    }
}
