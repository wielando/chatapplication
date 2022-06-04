package ChatWebSocket.Database.Mapper;

import ChatWebSocket.Database.DataMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ClientInfoMapper extends DataMapper {


    private ResultSet executedStatement = null;

    public ResultSet executeStatementWithParams(String statement, HashMap<String, Object> params) throws SQLException {
        PreparedStatement sqlStatement = this.buildStatementWithParams(statement, params);

        return this.executedStatement = sqlStatement.executeQuery();
    }


}
