package Books;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import InputValidationUtils.ValidationUtils;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class Feedback {
    private String userId;
    private String date;
    private String feedback;

    // Default constructor
    public Feedback() {
    }

    // Parameterized constructor
    public Feedback(String userId, String feedback) {
        this.userId = userId;
        this.date = getCurrentDate();  // Automatically set current date
        this.feedback = feedback;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public String getFeedback() {
        return feedback;
    }

    // Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    // Method to get the current date in yyyy-mm-dd format
    private String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();  // Get today's date
        return currentDate.toString();  // Format as yyyy-mm-dd
    }

    // Method to collect user input with validation
    public void getUserInput(String userId) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please provide a clear and valid Feeadback.");
        // Get userId (max length 20)
        this.userId = userId;

        // Get feedback (max length 100)
        this.feedback = getUserInput(scanner, "Enter your feedback: ", "Feedback cannot be empty and must be less than 100 characters.", 100);

        // Automatically set the current date
        this.date = getCurrentDate();
    }

    // Helper method to get validated input from the user
    private String getUserInput(Scanner scanner, String prompt, String errorMessage, int maxLength) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            // Check if input is not empty and does not exceed max length
            if (!input.isEmpty() && input.length() <= maxLength) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "\u001B[32m%-15s\u001B[0m \u001B[34m%-15s\u001B[0m \u001B[35m%-50s\u001B[0m",
                userId, date, feedback
        );
    }

    public static void displayNullValues() {
        System.out.printf(
                "\u001B[32m%-15s\u001B[0m \u001B[34m%-15s\u001B[0m \u001B[35m%-50s\u001B[0m%n",
                "N/A", "N/A", "N/A"
        );
    }

    public static String getHeader() {
        return String.format(
                "\u001B[32m%-15s\u001B[0m \u001B[34m%-15s\u001B[0m \u001B[35m%-50s\u001B[0m",
                "UserID", "Date", "Feedback"
        );
    }

    public static void displayFeedbacks() throws Exception {
        String feedbackBooksFilePath = "Databases//Feedback.csv";
        List<Feedback> feedbacks = CSVReader.readCSV(feedbackBooksFilePath, Feedback.class);
        System.out.println(Feedback.getHeader());
        System.out.println("-".repeat(100));
        if (!feedbacks.isEmpty()) {
            for (Feedback feedback : feedbacks) {
                System.out.println(feedback);
            }
        } else {
            Feedback.displayNullValues();
        }
        System.out.println("-".repeat(100));
    }

}