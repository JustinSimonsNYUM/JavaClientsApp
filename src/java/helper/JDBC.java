package helper;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * class JDBC.java
 */

/**
 * @author Justin Simons
 * */

/**
 * DBQuery class handles connecting to the database
 * */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * openConnection opens the connection to the database and sets connection the driver.
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * getConnection returns the connection
     * @return connection
     */
    public static Connection getConnection(){
        return connection;
    }

    /**
     * closeConnection closes the database connection and prints Connection closed!
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            //Don't do anything
        }
    }
}
