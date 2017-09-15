package com;

import test.Constructor;
import test.DbManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");

        //Добавляем БД-менеджер
        DbManager db = new DbManager();
        Connection conn = db.getConnection();

        db.writeTable();
        List<Constructor> list = db.readTable();

        String d = list.get(1).getName().toString();
        System.out.println(d);

        String log = request.getParameter("login");
        String pass = request.getParameter("password");

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");
            out.println("</head>");
            out.println("<body>");

            /*if(log.equals("Admin") && pass.equals("1111")){
                out.println("<h1>Hello, " + request.getParameter("login") + "</h1>");
            } else{
                out.println("<h1>Try again!</h1>");
            }*/

            if(conn != null){
                out.println("<h1>Hello!!!</h1>");
            } else{
                out.println("<h1>Try again!</h1>");
            }

            out.println("</body>");
            out.println("</html>");
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