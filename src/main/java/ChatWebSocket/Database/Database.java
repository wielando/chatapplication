package ChatWebSocket.Database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;

public class Database {

    private DatabasePool databasePool;
    private HikariDataSource dataSource;

    public Database() {

        boolean SQLException = false;

        try {
            this.databasePool = new DatabasePool();

            if (!this.databasePool.getPoolConfiguration()) {
                System.out.println("Failed to establish a MySQL Connection. Critical error - system shutdown!");
                SQLException = true;
                return;
            }

            this.dataSource = this.databasePool.getDatabase();

        } catch (Exception e) {
            System.out.println("Failed to establish a MySQL Connection. Critical error - system shutdown!");
            SQLException = true;
        } finally {
            if (SQLException) {
                System.exit(0);
            }
        }
    }

    public HikariDataSource getDataSource() {
        return this.dataSource;
    }

    public DatabasePool getDatabasePool() {
        return this.databasePool;
    }

    public void dispose() throws SQLException {
        if (this.databasePool != null) {
            this.databasePool.getDatabase().close();
        }

        this.dataSource.close();
    }

}
