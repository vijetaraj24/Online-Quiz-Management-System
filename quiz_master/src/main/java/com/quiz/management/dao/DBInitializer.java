package com.quiz.management.dao;

import java.sql.Connection;
import java.sql.Statement;

public class DBInitializer {
    public static void initialize() {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();

            // Create tables once
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(100)," +
                    "email VARCHAR(100) UNIQUE," +
                    "password VARCHAR(100)" +
                    ");";

            String createQuizzesTable = "CREATE TABLE IF NOT EXISTS quizzes (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "title VARCHAR(100)," +
                    "description TEXT" +
                    ");";

            String createQuestionsTable = "CREATE TABLE IF NOT EXISTS questions (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "quiz_id INT," +
                    "question_text TEXT," +
                    "option_a VARCHAR(100)," +
                    "option_b VARCHAR(100)," +
                    "option_c VARCHAR(100)," +
                    "option_d VARCHAR(100)," +
                    "correct_option CHAR(1)," +
                    "FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE" +
                    ");";

            String createResultsTable = "CREATE TABLE IF NOT EXISTS results (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "user_id INT," +
                    "quiz_id INT," +
                    "score INT," +
                    "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE" +
                    ");";

            stmt.execute(createUsersTable);
            stmt.execute(createQuizzesTable);
            stmt.execute(createQuestionsTable);
            stmt.execute(createResultsTable);

            System.out.println("✅ Tables initialized (only if not existing).");

        } catch (Exception e) {
            System.out.println("❌ Failed to initialize database:");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.out.println("⚠️ Error closing resources:");
                e.printStackTrace();
            }
        }
    }
}
