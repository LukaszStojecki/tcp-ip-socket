package util;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

@AllArgsConstructor
public class JDBCTransactionManager {

    public JDBCConfiguration jdbcConfiguration;

    public <T> T getInTransaction(Function<Connection, T> connectionFunction) {
        T result;
        try (
                final Connection connection = jdbcConfiguration.getConnection()
        ) {
            connection.setAutoCommit(false);

            result = connectionFunction.apply(connection);

            connection.commit();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        return result;
    }
}
