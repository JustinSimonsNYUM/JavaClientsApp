package com.example.javaclientsapp;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("logIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 200);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Java Client App");
        stage.show();
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
    /*Connection connect = JDBC.getConnection();
        String insertStatement = "INSERT INTO countries (Country, Create_Date, Created_By, Last_Updated_By) VALUES (?,?,?,?)";

        DBQuerys.setPreparedStatement(connect, insertStatement);
        PreparedStatement ps = DBQuerys.getPreparedStatement();

        String countryName;
        String date = "2021-11-12 00:00:00";
        String createdBy = "admin";
        String lastUpdatedBy = "admin";

        Scanner keyboard = new Scanner(System.in);
        countryName = keyboard.nextLine();*/
    /*how to process a result set to use data from a DB
    Connection connect = JDBC.getConnection();
        DBQuerys.setStatement(connect);
        Statement statement = DBQuerys.getStatement();
        try{
            String s = "SELECT * from countries";
            statement.execute(s);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                int Country_ID = rs.getInt("Country_ID");
                String Country = rs.getString("Country");
                LocalDate date = rs.getDate("Create_Date").toLocalDate();
                LocalTime time = rs.getTime("Create_Date").toLocalTime();
                String Created_By = rs.getString("Created_By");
                LocalDateTime Last_Update = rs.getTimestamp("Last_Update").toLocalDateTime();

                System.out.println(Country_ID + " | " + Country + " | " + date + " | " + time + " | " + Created_By + " | " + Last_Update);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
*/
    /* how to use SQL query on DB
    Connection connect = JDBC.getConnection();
        DBQuerys.setStatement(connect);
        Statement statement = DBQuerys.getStatement();
        String insertSQL = "INSERT INTO countries (Country, Create_Date, Created_By, Last_Updated_By)" +
                "VALUES ('US', '2021-11-12', 'admin', 'admin')";

        statement.execute(insertSQL);

        //rows affected
        if(statement.getUpdateCount() > 0)
            System.out.println(statement.getUpdateCount() + " rows affected in statement.");
        else
            System.out.println("nothing was added");
        */
}