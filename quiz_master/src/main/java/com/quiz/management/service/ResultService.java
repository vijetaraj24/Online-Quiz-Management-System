package com.quiz.management.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.quiz.management.dao.*;

public class ResultService {

    public static class ScoreboardEntry {
        public String username;
        public int score;

        public ScoreboardEntry(String username, int score) {
            this.username = username;
            this.score = score;
        }
    }

    public List<ScoreboardEntry> getScoreboardForQuiz(int quizId) {
        List<ScoreboardEntry> scoreboard = new ArrayList<>();

        String sql = "SELECT u.name AS name, r.score AS score " +
                "FROM users AS u " +
                "LEFT JOIN results AS r ON u.id = r.user_id " +
                "WHERE r.quiz_id = ? " +
                "ORDER BY r.score DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("name");
                int score = rs.getInt("score");
                scoreboard.add(new ScoreboardEntry(username, score));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoreboard;
    }
}

