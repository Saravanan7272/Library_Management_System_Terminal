package UserActions;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import InputValidationUtils.ValidationUtils;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import java.io.*;
import java.util.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;

public class UserActionHandler {

   // private Scanner scanner = new Scanner(System.in);

    // Method for viewing account summary
    public void viewAccountSummary(Scanner scanner,String userId) throws Exception {
       // String userId = ValidationUtils.promptStringWithValidation("Enter your user ID (7 digits) for account summary", 3, ValidationUtils::isValidUserId,ValidationUtils.getUserIdErrorMessage());
        if (userId == null) return ;
        ViewAccountSummary viewAccountSummary = new ViewAccountSummary(new UserActionValidator());
        viewAccountSummary.displaySummary(userId,scanner);
    }

    public void giveFeedback(String userId) {
        try {
            Feedback feedback = new Feedback();
            feedback.getUserInput(userId);

            String filepath = "Databases/Feedback.csv";
            // Write feedback to CSV without reading it back
            CSVWriter.writeObjectsToCSV(filepath, Collections.singletonList(feedback), 0);

        } catch (Exception e) {
            System.out.println("Error occurred while giving feedback: " + e.getMessage());
        }
    }

    public void requestBook(String userId) {
        try {
            BookRequest bookRequest = new BookRequest();
            bookRequest.getUserInput(userId);

            String filepath = "Databases/RequestBooks.csv";
            // Write request to CSV without reading it back
            CSVWriter.writeObjectsToCSV(filepath, Collections.singletonList(bookRequest), 0);

        } catch (Exception e) {
            System.out.println("Error occurred while requesting book: " + e.getMessage());
        }
    }

}