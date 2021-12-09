package helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

    private static Statement statement;
    private static PreparedStatement preparedStatement;

    //Create a statement object here
    public static void setStatement(Connection connect) throws SQLException {
        statement = connect.createStatement();
    }
    public static Statement getStatement(){
        return statement;
    }

    public static void setPreparedStatement(Connection connect, String sqlQuery) throws SQLException{
        preparedStatement = connect.prepareStatement(sqlQuery);
    }

    public static PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }


}
