package ro.softvision.data.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.softvision.data.entity.User;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("userTemplateRepo")
public class JdbcTemplateUserRepo implements UserRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTemplateUserRepo.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<User> rowMapper = new UserRowMapper();

    public JdbcTemplateUserRepo(JdbcTemplate jdbcTemplate,
                                NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<User> findById(Long id) {
        final String sql = "SELECT * FROM P_USER WHERE ID = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByIdUsingNamedParameters(Long id) {
        final String sql = "SELECT * FROM P_USER WHERE ID = :ID";
        try {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("ID", id);
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, parameters, rowMapper));
        } catch (EmptyResultDataAccessException erdae) {
            return Optional.empty();
        }
    }

    @Override
    public Integer countUsers() {
        final String sql = "SELECT COUNT(*) FROM P_USER";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Set<User> findAll() {
        final String sql = "SELECT * FROM P_USER";
        return new HashSet<>(jdbcTemplate.query(sql, rowMapper));
    }

    @Override
    public void printHtmlAllByName(String name) {
        final String sql = "SELECT * FROM P_USER WHERE USERNAME = ?";
        jdbcTemplate.query(sql, new HTMLUserRowCallbackHandler(System.out), name);
    }

    /* * * * * * * * *
     *  ROW  MAPPER  *
     * * * * * * * * */
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

    /* * * * * * * * * * * * *
     *  ROW CALLBACK HANDLER *
     * * * * * * * * * * * * */
    private class HTMLUserRowCallbackHandler implements RowCallbackHandler {

        private final PrintStream out;

        HTMLUserRowCallbackHandler(PrintStream out) {
            this.out = out;
        }

        @Override
        public void processRow(ResultSet rs) throws SQLException {

            final String newLine = "</br>\n";
            final String paragraphStart = "<p>";
            final String paragraphEnd = "</p>";
            final String username = "USERNAME";
            final String id = "ID";
            final String email = "EMAIL";

            final String htmlSb = paragraphStart + id + ": " + rs.getLong(id) + paragraphEnd +
                    newLine +
                    paragraphStart + username + ": " + rs.getString(username) + paragraphEnd +
                    newLine +
                    paragraphStart + email + ": " + rs.getString(email) + paragraphEnd +
                    newLine;

            out.print(htmlSb);
        }
    }

    /* * * * * * * * * * * * *
     *  RESULT SET EXTRACTOR *
     * * * * * * * * * * * * */
    private class JohnUsersExtractor implements ResultSetExtractor<User> {

        @Override
        public User extractData(ResultSet rs) throws SQLException, DataAccessException {
            return null;
        }
    }
}
