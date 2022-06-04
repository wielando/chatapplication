package ChatWebSocket.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class DataMapper {

    private PreparedStatement statement;
    private Database database;

    public DataMapper() {
        this.database = new Database();
    }

    protected PreparedStatement buildStatement(String statement) {

        try (Connection connection = this.database.getDataSource().getConnection(); PreparedStatement stmt = connection.prepareStatement(statement)) {
            return this.statement = stmt;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected PreparedStatement buildStatementWithParams(String statement, HashMap<String, Object> params) throws SQLException {

        try {
            Connection connection = this.database.getDataSource().getConnection();
            PreparedStatement stmt = connection.prepareStatement(statement);

            int i = 1;

            for (Map.Entry<String, Object> set : params.entrySet()) {

                if (set.getValue().getClass() == String.class) {
                    stmt.setString(i, (String) set.getValue());
                } else if (set.getValue().getClass() == Integer.class) {
                    stmt.setInt(i, (Integer) set.getValue());
                } else if (set.getValue().getClass() == Double.class) {
                    stmt.setDouble(i, (Double) set.getValue());
                } else if (set.getValue().getClass() == Float.class) {
                    stmt.setFloat(i, (Float) set.getValue());
                } else if (set.getValue().getClass() == Long.class) {
                    stmt.setLong(i, (Long) set.getValue());
                }

                i++;
            }

            return stmt;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
