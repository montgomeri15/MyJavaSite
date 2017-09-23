package com;

import test.Constructor;
import test.DbManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewServlet extends HttpServlet {

    DbManager db = new DbManager();

    public static Connection connection;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        /***Подключение к БД***/
        connection = db.getConnection();

        /***Считывание таблицы***/
        List<Constructor> list = db.readTable();

        for (int i = 0; i<list.size(); i++){

            String dbNames = list.get(i).getName().toString();
            String dbPasswords = list.get(i).getPass().toString();

            if (login.equals(dbNames) && password.equals(dbPasswords)){

                out.println("<!DOCTYPE html><html><head><title>Successful authorization</title></head><body>");
                out.println("<h1>Hello, " + login + "! </h1><br><h2>Authorization completed successfully.</h2>");
                out.println("<a href=\"http://localhost:8080/game.html\"><h3>Let's play!</h3></a>");
                out.println("</body></html>");
                break;

            } else{
                out.println("<!DOCTYPE html><html><head><title>Successful authorization</title></head><body>");
                out.println("<h1>Hello, " + login + "! </h1><br><h2>Ooops!</h2>");
                out.println("<a href=\"http://localhost:8080/index.html\"><h3>Try again</h3></a>");
                out.println("</body></html>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}