package TestAppV0;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/TestAppV0")
public class TestAppV0 extends HttpServlet {
    private String name;
    private String comment;
    private String str;
    private String str1;
    private String str2;
    int count = 0;
    public TestAppV0() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    // request for connection with database MySQL
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException {
        setName(req.getParameter("nameUser"));
        setComment(req.getParameter("nameComment"));
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
            if(count == 0){
                statement.executeUpdate("drop table TestAppUsers");

            }
            // create table TestAppUsers and put values from servlet form
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS TestAppUsers (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL , comment CHAR(255) NOT NULL , PRIMARY KEY(id));");
            PreparedStatement preparedStatement = connection.prepareStatement("insert into TestAppUsers (name,comment) values(?,?)");
            preparedStatement.setString(1, req.getParameter("nameUser"));
            preparedStatement.setString(2, req.getParameter("nameComment"));
            ResultSet resultSet = preparedStatement.executeQuery("select * from TestAppUsers");

            // Push result from database MySQL in servlet
            while (resultSet.next()){
                str = "<br> \nid: " + resultSet.getString(1) + "<br> \nname : " + resultSet.getString(2) +
                        "<br> \ncomment : " + resultSet.getString(3);
            }
            str2 = str1 += str;

            preparedStatement.executeUpdate();
            preparedStatement.close();
            count++;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //  html servlet
                resp.getWriter().write("<html>" +
                        "<head></head>" +
                        "<body>" +
                        "<title>Test app</title>" +
                        "<form action='TestAppV0 ' method='post' >" +
                        "<br>Name" +
                        "<br><input type='text' name='nameUser'>" +
                        "<br>Comment" +
                        "<br><textarea name='nameComment'></textarea>" +
                        "<br><input type='submit' name='submit' value='send'>" +
                        "</form>" +
                        "<br>Last user name: " + getName() +
                        "<br>Last user comment: " + getComment() +
                        "<br>result: " + showList() +
                        "</body>" +
                        "</html>");

    }
    public String showList(){
        if(count==0)
        return str2.substring(12);
        else return str2.substring(16) ;
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
