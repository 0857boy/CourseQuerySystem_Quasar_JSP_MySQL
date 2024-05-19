package com.example.coursemagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONObject;
import java.io.BufferedReader;


import static java.lang.System.out;



@WebServlet(name = "CourseServlet", urlPatterns = {"/api/courses/*"})
public class CourseServlet extends HttpServlet {
    private String url = "jdbc:mysql://localhost:3306/coursemanagement";
    private String user = "root";
    private String password = "13579";




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAccessControlHeaders(response);
        loadJDBC();


        PrintWriter out = response.getWriter();

        try {
            if (!request.getParameter("command").isEmpty()) {
                if (request.getParameter("command").equals("getCourse")){
                    getCourse(request, response);
                }
                else if (request.getParameter("command").equals("addCourse")){
                    addCourse(request, response);
                }
                else if (request.getParameter("command").equals("deleteCourse")){
                    deleteCourse(request, response);
                }
                else if (request.getParameter("command").equals("updateCourse")){
                    updateCourse(request, response);
                }
            }
        } catch (Exception e) {
            out.println("{ \"error\": \"" + e.getMessage() + "\" }");
            out.flush();
        }
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        resp.setContentType("application/json");
    }

    private void loadJDBC () {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            out.println("Could not load the driver");
        }
    }

    private void getCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            // 連接資料庫
            Connection conn = DriverManager.getConnection(url, user, password);
            // 執行 SQL 語法
            String sql = "SELECT * FROM courses";
            if (!request.getParameter("id").isEmpty()) {
                sql += " WHERE id = ?";
                sql = sql.replace("?", '\'' + request.getParameter("id") + '\'' ) ;
            }
            PreparedStatement statement = conn.prepareStatement(sql);
            // 取得查詢結果
            ResultSet result = statement.executeQuery();
            // 將查詢結果轉換成 JSON 格式
            out.println("[");
            while (result.next()) {
                out.println("{");
                out.println("\"id\": \"" + result.getString("id") + "\",");
                out.println("\"name\": \"" + result.getString("name") + "\",");
                out.println("\"description\": \"" + result.getString("description") + "\"");
                out.println("}");
                if (!result.isLast()) {
                    out.println(",");
                }
            }
            out.println("]");
            conn.close();
        } catch (Exception e) {
            out.println("{ \"error\": \"" + e.getMessage() + "\" }");
            out.flush();
        }
    }


    private void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO courses (id, name, description) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);


            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String description = request.getParameter("description");

            if (id == null || name == null || description == null) {
                out.println("{ \"error\": \"Missing course data\" }");
                return;
            }

            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.executeUpdate();
            out.println("{ \"success\": \"Course added successfully\" }");
        } catch (Exception e) {
            out.println("{ \"error\": \"" + e.getMessage() + "\" }");
        } finally {
            out.flush();
        }
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM courses WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, request.getParameter("id"));
            statement.executeUpdate();
            out.println("{ \"success\": \"Course deleted successfully\" }");
        } catch (Exception e) {
            out.println("{ \"error\": \"" + e.getMessage() + "\" }");
        } finally {
            out.flush();
        }
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "UPDATE courses SET name = ?, description = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, request.getParameter("name"));
            statement.setString(2, request.getParameter("description"));
            statement.setString(3, request.getParameter("id"));
            statement.executeUpdate();
            out.println("{ \"success\": \"Course updated successfully\" }");
        } catch (Exception e) {
            out.println("{ \"error\": \"" + e.getMessage() + "\" }");
        } finally {
            out.flush();
        }
    }

}