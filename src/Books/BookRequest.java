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
public class BookRequest {
    private String userId;
    private String bookDetails;
    private String requestReason;

    // Constructor
    public BookRequest() {
        this.userId = "";
        this.bookDetails = "";
        this.requestReason = "";
    }

    // Helper method to get validated input from the user
    private String getUserInput(Scanner scanner, String prompt, String errorMessage, int maxLength) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (!input.isEmpty() && input.length() <= maxLength) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    // Method to get input from the user (student) with validation
    public void getUserInput(String userId) {
        Scanner sc = new Scanner(System.in);
        int MAX_REQUEST_REASON_LENGTH = 30;
        System.out.println("Please provide a clear and valid reason for your request.");
       // String userId = ValidationUtils.promptStringWithValidation("Enter User ID (7 digits)", 3, ValidationUtils::isValidUserId,ValidationUtils.getUserIdErrorMessage());
        if(userId==null)return;;
        this.bookDetails = getUserInput(sc, "Enter book details (e.g:name, author): ", "Book details cannot be empty..Enter Again.", 50);
        this.requestReason = getUserInput(sc, "Enter request reason (e.g: 'Increase Quantity' or 'Request New Book'): ",
                "Request reason cannot be empty and must be less than " + MAX_REQUEST_REASON_LENGTH + " characters.Enter Again.",
                MAX_REQUEST_REASON_LENGTH);
        System.out.println("Request sent");
    }
    @Override
    public String toString() {
        return String.format(
                "\u001B[32m%-20s\u001B[0m \u001B[34m%-50s\u001B[0m \u001B[35m%-50s\u001B[0m",
                userId, bookDetails, requestReason
        );
    }

    public static void displayNullValues() {
        System.out.printf(
                "\u001B[32m%-20s\u001B[0m \u001B[34m%-50s\u001B[0m \u001B[35m%-50s\u001B[0m%n",
                "N/A", "N/A", "N/A"
        );
    }

    public static String getHeader() {
        return String.format(
                "\u001B[32m%-20s\u001B[0m \u001B[34m%-50s\u001B[0m \u001B[35m%-50s\u001B[0m",
                "UserID", "BookDetails", "RequestReason"
        );
    }
    public static void displayRequestBooks() throws Exception {
        String requestBooksFilePath = "Databases//RequestBooks.csv";
        List<BookRequest> requestedBooks = CSVReader.readCSV(requestBooksFilePath, BookRequest.class);
        System.out.println(BookRequest.getHeader());
        System.out.println("-".repeat(120));
        if (!requestedBooks.isEmpty()) {
            for (BookRequest requestedBook : requestedBooks) {
                System.out.println(requestedBook);
            }
        } else {
            BookRequest.displayNullValues();
        }
        System.out.println("-".repeat(120));
    }


}
