package com.quiz.management.service;
import com.quiz.management.dao.*;
import com.quiz.management.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class QuizService {
    private final Scanner sc = new Scanner(System.in);

    private final String[][] questions = {
        {"1","1",",What is the size of int in Java?", "2", "4", "8", "16", "B"},
        {"2","1","Which keyword is used to inherit a class?", "this", "import", "extends", "implements", "C"},
        {"3","1",",What is the default value of boolean?", "true", "false", "0", "null", "B"},
        {"4","1","What does JVM stand for?", "Java Virtual Machine", "Java Volume Machine", "Java Verified Manager", "Java Value Module", "A"},
        {"5","1","Which method is the entry point in Java?", "start()", "main()", "run()", "begin()", "B"},
        {"6","1","What is the real job of a constructor in Java?", " Construct buildings", "Build objects", "Destroy objects", "Confuse programmers", "B"},
        {"7","1","Which keyword in Java lets a class inherit superhero powers from another class?", 
        	" inherits", "powers", "extends", "imports", "C"}
    };

    public int startQuiz(User user) {
        System.out.println("\n--- Quiz Started ---");
        int score = 0;
        for (int i = 0; i < questions.length; i++) {
            System.out.println("\nQ" + (i + 1) + ". " + questions[i][2]);
            System.out.println("A. " + questions[i][3]);
            System.out.println("B. " + questions[i][4]);
            System.out.println("C. " + questions[i][5]);
            System.out.println("D. " + questions[i][6]);
            System.out.print("Your answer: ");
            String ans = sc.nextLine().toUpperCase();

            if (ans.equals(questions[i][7])) {
                score++;
            }
        }

        saveResult(user.getId(), score);
        return score;
    }

    private void saveResult(int userId, int score) {
        int quizId = 1; // assuming you only have one quiz for now
        String sql = "INSERT INTO results(user_id, quiz_id, score) VALUES(?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, quizId);
            stmt.setInt(3, score);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("❌ Failed to save result.");
            e.printStackTrace();
        }
    }

}

