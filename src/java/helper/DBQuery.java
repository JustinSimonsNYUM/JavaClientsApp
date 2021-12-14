package helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * class DBQuery.java
 */

/**
 * @author Justin Simons
 * */

/**
 * DBQuery class handles the database statements and prepared statements.
 * */
public class DBQuery {

    private static Statement statement;
    private static PreparedStatement preparedStatement;

    /**
     * setStatement creates a new statement
     * @param connect holds the connection to the database
     * @throws SQLException is thrown in a connection to the database is not made.
     */
    public static void setStatement(Connection connect) throws SQLException {
        statement = connect.createStatement();
    }

    /**
     * getStatement gets the statement
     * @return returns statement
     */
    public static Statement getStatement(){
        return statement;
    }
    /**
     * setPreparedStatement sets creates a new prepared statement
     * @param connect holds the connection to the database
     * @throws SQLException is thrown in a connection to the database is not made.
     */
    public static void setPreparedStatement(Connection connect, String sqlQuery) throws SQLException{
        preparedStatement = connect.prepareStatement(sqlQuery);
    }
    /**
     * getPreparedStatement gets the PreparedStatement
     * @return returns preparedStatement
     */
    public static PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }


}
