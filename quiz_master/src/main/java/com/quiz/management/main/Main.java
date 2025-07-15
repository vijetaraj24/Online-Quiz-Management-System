package com.quiz.management.main;
import java.sql.Connection;
import com.quiz.management.dao.*;

import com.quiz.management.models.*;
import com.quiz.management.service.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService();
        QuizService quizService = new QuizService();

        User currentUser = null;
        Boolean val = true; 
        while (val) {
            System.out.println("\n==== Welcome to the Online Quiz System ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    currentUser = userService.registerUser();
                    break;
                case 2:
                    currentUser = userService.loginUser();
                    break;
                 
                default:
                    System.out.println("❌ Invalid choice.");
            }

            if (currentUser != null) {
                System.out.println("\n✅ Welcome, " + currentUser.getName() + "!");
                int score = quizService.startQuiz(currentUser);
                System.out.println("\n🎉 Quiz Completed!");
                System.out.println("Your Score: " + score + " out of 7");
                currentUser = null; // logout after quiz
            }
            
            ResultService resultService = new ResultService();
            List<ResultService.ScoreboardEntry> scoreboard = resultService.getScoreboardForQuiz(1); // quiz ID

            if (scoreboard.isEmpty()) {
                System.out.println("No scores found for this quiz.");
            } else {
                System.out.println("📊 Scoreboard:");
                for (ResultService.ScoreboardEntry entry : scoreboard) {
                    System.out.println(entry.username + " - " + entry.score);
                }
            }
            System.out.println("Want to exit Press 1 , for continue Press 0");
            int byeVal = sc.nextInt();
            if(byeVal == 1) {
	            System.out.println("👋 Thank you for using the Quiz System!");
	            val = false;
            }
        }
    }
}

