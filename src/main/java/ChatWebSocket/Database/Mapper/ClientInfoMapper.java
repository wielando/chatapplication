package ChatWebSocket.Database.Mapper;

import ChatWebSocket.Database.DataMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ClientInfoMapper extends DataMapper {


    private ResultSet executedStatement = null;

    public ClientInfoMapper executeStatementWithParams(String statement, HashMap<String, Object> params) throws SQLException {
        PreparedStatement sqlStatement = this.buildStatementWithParams(statement, params);

        this.executedStatement = sqlStatement.executeQuery();

        return this;
    }

    public String getUsername() throws SQLException {
        return this.executedStatement.getString("username");
    }


}
