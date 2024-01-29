package com.example.ducklings.repository;


import com.example.ducklings.models.InvoiceModel;
import com.example.ducklings.models.UserModel;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DucklingsRepository {
    private Connection conn;
    //List<UserModel> users = new ArrayList<>();

    public DucklingsRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ducklingsdb?createDatabaseIfNotExist=true", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<InvoiceModel> getAll(String owner) {
        List<InvoiceModel> invoices = new ArrayList<>();
        String sql = "SELECT id, title, date, description, category, price FROM payments WHERE owner = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, owner);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {

                InvoiceModel entry = new InvoiceModel();

                entry.setId(rs.getInt("id"));
                entry.setTitle(rs.getString("title"));
                entry.setDate(rs.getDate("date"));
                entry.setDescription(rs.getString("description"));
                entry.setCategory(rs.getString("category"));
                entry.setPrice(rs.getBigDecimal("price"));
                invoices.add(entry);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return invoices;
    }

    public void createPaymentsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS payments ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "title VARCHAR(64), "
                + "date DATE, "
                + "description VARCHAR(1024), "
                + "category VARCHAR(255), "
                + "price DECIMAL(10, 2), "
                + "owner VARCHAR(64))";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(String title, Date date, String description, String category, BigDecimal price, String owner) {
        String sql =
                "INSERT INTO payments (title, date, description, category, price, owner)" +
                        "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setDate(2, date);
            pstmt.setString(3, description);
            pstmt.setString(4, category);
            pstmt.setBigDecimal(5, price);
            pstmt.setString(6, owner);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id){
        String sql = "DELETE FROM payments WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, String title, Date date, String description, String category, BigDecimal price, String owner){
        String sql =
                "UPDATE payments SET title = ?, date = ?, description = ?, category = ?, price = ?, owner = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setDate(2, date);
            pstmt.setString(3, description);
            pstmt.setString(4, category);
            pstmt.setBigDecimal(5, price);
            pstmt.setString(6, owner);
            pstmt.setInt(7, id);

            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsers() {

        String sql = "CREATE TABLE IF NOT EXISTS users (id INT(2) AUTO_INCREMENT PRIMARY KEY, name varchar(255), password varchar(255))";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sql = "TRUNCATE TABLE users";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sql = "INSERT INTO users (name, password) VALUES ('Adam', 'pass123'), ('Jakob', 'pass123'), ('Yves', 'pass123'), ('Elin', 'pass123')";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserModel> getAllUsers(){
        List<UserModel> users = new ArrayList<>();
        String sql = "SELECT name, password FROM users";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                UserModel user = new UserModel();

                user.setName(rs.getString("Name"));
                user.setPassword(rs.getString("Password"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public UserModel getUserByUsername(String username) {
        UserModel user = null;
        String sql = "SELECT name, password FROM users WHERE name = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserModel();
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


}
