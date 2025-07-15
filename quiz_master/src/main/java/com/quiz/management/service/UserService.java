package com.quiz.management.service;

import com.quiz.management.dao.*;
import com.quiz.management.models.*;

import java.sql.*;
import java.util.Scanner;

public class UserService {
    Scanner sc = new Scanner(System.in);

    public User registerUser() {
        System.out.println("\n--- Register ---");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new User(id, name, email, password);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Registration failed. Email might already be in use.");
        }

        return null;
    }

    public User loginUser() {
        System.out.println("\n--- Login ---");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
            } else {
                System.out.println("❌ Invalid credentials.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
