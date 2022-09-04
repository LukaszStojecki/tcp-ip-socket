package dao.impl;

import dao.UserDao;
import lombok.AllArgsConstructor;
import model.User;
import util.JDBCTransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
public class UserDaoImpl implements UserDao {

    private static final String ID_FIELD = "id";
    private static final String NICK_FIELD = "nick";
    private static final String LOGIN_FIELD = "login";
    private static final String PASSWORD_FIELD = "password";
    private static final String INSERT_TIME_FIELD = "insert_time";
    private static final int PARAMETER_INDEX = 1;

    private JDBCTransactionManager jdbcTransactionManager;

    @Override
    public Optional<User> findByLogin(String login) {
        return jdbcTransactionManager.getInTransaction(connection -> {
            try {
                String query = "SELECT *"
                        + "FROM users "
                        + "WHERE login = ? ";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(PARAMETER_INDEX, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return getUser(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return Optional.empty();
        });
    }

    private Optional<User> getUser(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID_FIELD);
        String nick = resultSet.getString(NICK_FIELD);
        String userLogin = resultSet.getString(LOGIN_FIELD);
        String password = resultSet.getString(PASSWORD_FIELD);
        LocalDateTime localDateTime = resultSet.getTimestamp(INSERT_TIME_FIELD).toLocalDateTime();
        return Optional.ofNullable(User.builder()
                .id(id)
                .nick(nick)
                .login(userLogin)
                .password(password)
                .insertTime(localDateTime)
                .build());
    }
}

