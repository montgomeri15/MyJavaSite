package com;

import test.DbManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "RegServlet")
public class RegServlet extends HttpServlet {

    DbManager db = new DbManager();
    String username, password, passwordRepeat;

    public static Connection connection;
    public static PreparedStatement prepSt;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        username = request.getParameter("username");
        password = request.getParameter("password");
        passwordRepeat = request.getParameter("password_again");

        /***Подключение к БД***/
        connection = db.getConnection();

        /***Заполнение таблицы***/
        if (password.equals(passwordRepeat)) {
            prepSt = connection.prepareStatement("INSERT INTO `table` (Name, Pass) VALUES (?, ?)");
            prepSt.setString(1, username);
            prepSt.setString(2, password);
            prepSt.executeUpdate();

            out.println("<!DOCTYPE html><html><head><title>Successful registration</title></head><body>");
            out.println("<h1>Hello, " + username + "! </h1><br><h2>Registration completed successfully.</h1>");
            out.println("</body></html>");
        } else {
            out.println("<!DOCTYPE html><html><head><title>Unsuccessful registration</title></head><body>");
            out.println("<h1>Hello, " + username + "! </h1><br><h2>Registration failed. Confirm your password.</h1>");
            out.println("<br><a href=\"http://localhost:8080/reg.html\">Try again.</a>");
            out.println("</body></html>");
        }

        /***Считывание таблицы***/
        db.readTable();


            /*if(user.equals("Admin") && pass.equals("1111")){
                out.println("<h1>Hello, " + request.getParameter("username") + "</h1>");
            } else{
                out.println("<h1>Try again!</h1>");
            }*/

            /*if(conn != null){
                out.println("<h1>Registration completed successfully.</h1>");
            } else{
                out.println("<h1>Try again!</h1>");
            }*/
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
