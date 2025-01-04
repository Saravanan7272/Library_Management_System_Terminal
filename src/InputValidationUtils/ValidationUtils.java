//package InputValidationUtils;
//
//import java.util.Scanner;
//import java.util.regex.Pattern;
//
//public class ValidationUtils {
//
//    private static final String EMAIL_FORMAT = "[a-zA-Z0-9._%+-]+@gmail.com";
//    private static final String[] VALID_DEPARTMENTS = {"CSE", "IT", "EEE", "ECE", "BME", "MECH", "CIVIL"};
//    private static final String[] VALID_BOOK_GROUPS = {"CSE", "IT", "EEE", "ECE", "BME", "MECH", "CIVIL"};
//    private static final Scanner scanner = new Scanner(System.in);
//
//    // Functional interface for input validation
//    @FunctionalInterface
//    public interface InputValidator<T> {
//        boolean isValid(T input);
//    }
//
//    // Validates User ID (7 digits)
//    public static boolean isValidUserId(String userId) {
//        return userId != null && userId.length() == 7 && userId.matches("\\d+");
//    }
//
//    // Validates Username (at least 3 characters)
//    public static boolean isValidUsername(String username) {
//        return username != null && username.length() >= 3;
//    }
//
//    // Validates Password (min 8 chars, mix of upper, lower, number, and special char)
//    public static boolean isValidPassword(String password) {
//        return password != null && password.length() >= 8 &&
//                password.matches(".*[a-z].*") &&
//                password.matches(".*[A-Z].*") &&
//                password.matches(".*[0-9].*") &&
//                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
//    }
//
//    // Validates Email (must end with @gmail.com)
//    public static boolean isValidEmail(String email) {
//        return email != null && email.matches(EMAIL_FORMAT);
//    }
//
//    // Validates Batch (Year format YYYY)
//    public static boolean isValidBatch(String batch) {
//        return batch != null && batch.matches("\\d{4}");
//    }
//
//    // Validates Department (matches against a predefined list)
//    public static boolean isValidDepartment(String department) {
//        for (String validDept : VALID_DEPARTMENTS) {
//            if (validDept.equalsIgnoreCase(department)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    public static boolean isValidYear(int year) {
//        return year >= 1 && year <= 4;
//    }
//
//    // Validates ISBN (starts with 978 or 979 followed by 10 digits)
//    public static boolean isValidIsbn(String isbn) {
//        return isbn != null && (isbn.matches("^978[0-9]{10}$") || isbn.matches("^979[0-9]{10}$"));
//    }
//
//
//    public static boolean isValidTitle(String title) {
//        return title != null && title.length() >= 5 && title.length() <= 100;
//    }
//
//
//    public static boolean isValidAuthor(String author) {
//        return author != null && author.length() >= 5 && author.length() <= 50;
//    }
//
//
//    public static boolean isValidPublisher(String publisher) {
//        return publisher != null && publisher.length() >= 5 && publisher.length() <= 50;
//    }
//
//
//    public static boolean isValidBookGroup(String group) {
//        for (String validGroup : VALID_BOOK_GROUPS) {
//            if (validGroup.equalsIgnoreCase(group)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // Validates Row Name (must follow format "Row X", where X is a number)
//    public static boolean isValidRowName(String rowName) {
//        return rowName != null && Pattern.matches("^Row\\s\\d+$", rowName);
//    }
//
//    // Validates Rack Number (must follow format "Rack X", where X is a letter)
//    public static boolean isValidRackNo(String rackNo) {
//        return rackNo != null && Pattern.matches("^Rack\\s[A-Z]$", rackNo);
//    }
//
//    // Helper method to prompt for a valid string input with retry logic
//    public static String promptStringWithValidation(String promptMessage, int maxAttempts, InputValidator<String> validator) {
//        int attempts = 0;
//        while (attempts < maxAttempts) {
//            System.out.print(promptMessage + ": ");
//            String input = scanner.nextLine().trim();
//            if (validator.isValid(input)) {
//                return input;
//            } else {
//                System.out.println("Invalid input. Please try again.");
//            }
//            attempts++;
//        }
//        System.out.println("Maximum attempts reached. Returning to the main menu...");
//        return null;  // Return null if validation fails after max attempts
//    }
//
//    // Helper method to prompt for a valid integer input with retry logic
//    public static int promptIntWithValidation(String promptMessage, int maxAttempts, InputValidator<Integer> validator) {
//        int attempts = 0;
//        while (attempts < maxAttempts) {
//            System.out.print(promptMessage + ": ");
//            try {
//                int input = Integer.parseInt(scanner.nextLine().trim());
//                if (validator.isValid(input)) {
//                    return input;
//                } else {
//                    System.out.println("Invalid input. Please try again.");
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid number format. Please try again.");
//            }
//            attempts++;
//        }
//        System.out.println("Maximum attempts reached. Returning to the main menu...");
//        return -1;  // Return -1 if validation fails after max attempts
//    }
//}
package InputValidationUtils;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final String EMAIL_FORMAT = "[a-zA-Z0-9._%+-]+@gmail.com";
    private static final String[] VALID_DEPARTMENTS = {"CSE", "IT", "EEE", "ECE", "BME", "MECH", "CIVIL"};
    private static final String[] VALID_BOOK_GROUPS = {"CSE", "IT", "EEE", "ECE", "BME", "MECH", "CIVIL"};
    private static final Scanner scanner = new Scanner(System.in);

    // Functional interface for input validation
    @FunctionalInterface
    public interface InputValidator<T> {
        boolean isValid(T input);
    }

    // Validates User ID (7 digits)
    public static boolean isValidUserId(String userId) {
        return userId != null && userId.length() == 7 && userId.matches("\\d+");
    }

    // Validates Username (at least 3 characters)
    public static boolean isValidUsername(String username) {
        return username != null && username.length() >= 3;
    }

    // Validates Password (min 8 chars, mix of upper, lower, number, and special char)
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }

    // Validates Email (must end with @gmail.com)
    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_FORMAT);
    }

    // Validates Batch (Year format YYYY)
    public static boolean isValidBatch(String batch) {
        return batch != null && batch.matches("\\d{4}");
    }

    // Validates Department (matches against a predefined list)
    public static boolean isValidDepartment(String department) {
        for (String validDept : VALID_DEPARTMENTS) {
            if (validDept.equalsIgnoreCase(department)) {
                return true;
            }
        }
        return false;
    }

    // Validates Year (between 1 and 4)
    public static boolean isValidYear(int year) {
        return year >= 1 && year <= 4;
    }

    // Validates ISBN (starts with 978 or 979 followed by 10 digits)
    public static boolean isValidIsbn(String isbn) {
        return isbn != null && (isbn.matches("^978[0-9]{10}$") || isbn.matches("^979[0-9]{10}$"));
    }

    // Validates Book Title (between 5 and 100 characters)
    public static boolean isValidTitle(String title) {
        return title != null && title.length() >= 5 && title.length() <= 50;
    }

    // Validates Author Name (between 5 and 50 characters)
    public static boolean isValidAuthor(String author) {
        return author != null && author.length() >= 5 && author.length() <= 50;
    }

    // Validates Publisher (between 5 and 50 characters)
    public static boolean isValidPublisher(String publisher) {
        return publisher != null && publisher.length() >= 5 && publisher.length() <= 50;
    }

    // Validates Book Group (matches against a predefined list)
    public static boolean isValidBookGroup(String group) {
        for (String validGroup : VALID_BOOK_GROUPS) {
            if (validGroup.equalsIgnoreCase(group)) {
                return true;
            }
        }
        return false;
    }

    // Validates Row Name (must follow format "Row X", where X is a number)
    public static boolean isValidRowName(String rowName) {
        return rowName != null && Pattern.matches("^Row\\s\\d+$", rowName);
    }

    // Validates Rack Number (must follow format "Rack X", where X is a letter)
    public static boolean isValidRackNo(String rackNo) {
        return rackNo != null && Pattern.matches("^Rack\\s[A-Z]$", rackNo);
    }

    // Helper method to prompt for a valid string input with retry logic
    public static String promptStringWithValidation(String promptMessage, int maxAttempts, InputValidator<String> validator, String errorMessage) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            System.out.print(promptMessage + ": ");
            String input = scanner.nextLine().trim();
            if (validator.isValid(input)) {
                return input;
            } else {
                System.out.println(errorMessage);  // Provide specific error message
            }
            attempts++;
        }
        System.out.println("Maximum attempts reached. Returning to the main menu...");
        return null;  // Return null if validation fails after max attempts
    }

    // Helper method to prompt for a valid integer input with retry logic
    public static int promptIntWithValidation(String promptMessage, int maxAttempts, InputValidator<Integer> validator, String errorMessage) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            System.out.print(promptMessage + ": ");
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (validator.isValid(input)) {
                    return input;
                } else {
                    System.out.println(errorMessage);  // Provide specific error message
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please try again.");
            }
            attempts++;
        }
        System.out.println("Maximum attempts reached. Returning to the main menu...");
        return -1;  // Return -1 if validation fails after max attempts
    }

    public static String getUserIdErrorMessage() {
        return "\u001B[31mUser ID must be exactly 7 digits long.\u001B[0m";
    }

    public static String getUsernameErrorMessage() {
        return "\u001B[31mUsername must be at least 3 characters long.\u001B[0m";
    }

    public static String getPasswordErrorMessage() {
        return "\u001B[31mPassword must contain at least 8 characters, including uppercase, lowercase, a number, and a special character.\u001B[0m";
    }

    public static String getEmailErrorMessage() {
        return "\u001B[31mEmail must be in the format: [username]@gmail.com.\u001B[0m";
    }

    public static String getBatchErrorMessage() {
        return "\u001B[31mBatch must be a valid year in YYYY format.\u001B[0m";
    }

    public static String getDepartmentErrorMessage() {
        return "\u001B[31mInvalid department. Please enter one of the following: CSE, IT, EEE, ECE, BME, MECH, CIVIL.\u001B[0m";
    }

    public static String getYearErrorMessage() {
        return "\u001B[31mYear must be between 1 and 4.\u001B[0m";
    }

    public static String getIsbnErrorMessage() {
        return "\u001B[31mISBN must start with 978 or 979 followed by 10 digits.\u001B[0m";
    }

    public static String getTitleErrorMessage() {
        return "\u001B[31mTitle must be between 5 and 50 characters long.\u001B[0m";
    }

    public static String getAuthorErrorMessage() {
        return "\u001B[31mAuthor name must be between 5 and 50 characters long.\u001B[0m";
    }

    public static String getPublisherErrorMessage() {
        return "\u001B[31mPublisher name must be between 5 and 50 characters long.\u001B[0m";
    }

    public static String getBookGroupErrorMessage() {
        return "\u001B[31mInvalid book group. Please enter one of the following: CSE, IT, EEE, ECE, BME, MECH, CIVIL.\u001B[0m";
    }

    public static String getTotalQuantityErrorMessage() {
        return "\u001B[31mError: Total Quantity must be a non-negative number.\u001B[0m";
    }

    public static String getAvailableQuantityErrorMessage() {
        return "\u001B[31mError: Available Quantity must be a non-negative number and cannot exceed Total Quantity.\u001B[0m";
    }

    public static String getRowNameErrorMessage() {
        return "\u001B[31mRow Name must be in the format 'Row X', where X is a number.\u001B[0m";
    }

    public static String getRackNoErrorMessage() {
        return "\u001B[31mRack Number must be in the format 'Rack X', where X is a letter.\u001B[0m";
    }

}
