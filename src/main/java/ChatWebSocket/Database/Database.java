package ChatWebSocket.Database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.PreparedStatement;

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

        } catch (Exception e) {
            System.out.println("Failed to establish a MySQL Connection. Critical error - system shutdown!");
            SQLException = true;
        } finally {
            if (SQLException) {
                System.exit(0);
            }
        }

        System.out.println("Database Connected!");

    }

    public DatabasePool getDatabasePool() {
        return this.databasePool;
    }
}
