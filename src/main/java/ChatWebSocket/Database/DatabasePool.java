package ChatWebSocket.Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;

public class DatabasePool {

    private HikariDataSource database;


    public boolean getPoolConfiguration() {
        try {
            HikariConfig databaseConfiguration = new HikariConfig();

            databaseConfiguration.setMaximumPoolSize(50);
            databaseConfiguration.setMinimumIdle(10);
            databaseConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/chatapplication");
            databaseConfiguration.addDataSourceProperty("serverName", "localhost");
            databaseConfiguration.addDataSourceProperty("port", 3306);
            databaseConfiguration.addDataSourceProperty("user", "root");
            databaseConfiguration.addDataSourceProperty("password", "");
            databaseConfiguration.addDataSourceProperty("cachePrepStmts", "true");
            databaseConfiguration.addDataSourceProperty("prepStmtCacheSize", "250");
            databaseConfiguration.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            this.database = new HikariDataSource(databaseConfiguration);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public HikariDataSource getDatabase() {
        return this.database;
    }
}
