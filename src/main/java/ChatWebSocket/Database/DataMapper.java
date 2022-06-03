package ChatWebSocket.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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




}
