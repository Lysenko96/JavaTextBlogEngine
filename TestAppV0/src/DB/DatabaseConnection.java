package DB;

import java.sql.*;
public class DatabaseConnection {
    public Connection connectionDB() throws SQLException {
        String userName = "root";
        String userPassword = "1234";
        String connectionUrl = "jdbc:mysql://localhost:3306/TestAppV0?serverTimezone=UTC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, userPassword);
             Statement statement = connection.createStatement()) {
       /*     if(count == 0){
                statement.executeUpdate("drop table TestAppUsers");

            }*/
            return connection;
        }
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

    }
}
