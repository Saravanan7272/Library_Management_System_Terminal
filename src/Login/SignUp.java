package Login;

import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Users.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import InputValidationUtils.ValidationUtils;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SignUp {
    private static final String mailFormat = "@gmail.com";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RED = "\u001B[31m";
    private LibraryUser newUser;
    private OTPHandler otpHandler = new OTPHandler();


    // Use the ValidationUtils class directly for validation
    public boolean addNewMember(Scanner scanner) {
        System.out.println(ANSI_RED + "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗" + ANSI_RESET);
        System.out.println(ANSI_RED + "║" + ANSI_BLUE + "Important Notice:" + "Please ensure that all information provided is accurate and follows these guidelines for future access!!!" + ANSI_RESET + ANSI_RED + "║" + ANSI_RESET);
        System.out.println(ANSI_RED + "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝" + ANSI_RESET);

        String userId = ValidationUtils.promptStringWithValidation("Enter User ID (7 digits)", 3, ValidationUtils::isValidUserId,ValidationUtils.getUserIdErrorMessage());
        if (userId == null) return false;

        SearchUsers searchUsers = new SearchUsers();
        if (searchUsers.checkIfUserExists(userId)) return false;

        String username = ValidationUtils.promptStringWithValidation("Enter Username (at least 3 characters)", 3, ValidationUtils::isValidUsername,ValidationUtils.getUsernameErrorMessage());
        if (username == null) return false;

        String password = ValidationUtils.promptStringWithValidation("Enter Password", 3, ValidationUtils::isValidPassword,ValidationUtils.getPasswordErrorMessage());
        if (password == null) return false;

        String type = getUserType(scanner);
        if (type == null) return false;

        String contactInfo = ValidationUtils.promptStringWithValidation("Enter Contact Info (Email)", 3, ValidationUtils::isValidEmail,ValidationUtils.getEmailErrorMessage());
        if (contactInfo == null) return false;

        if (type.equalsIgnoreCase("student")) {
            String batch = ValidationUtils.promptStringWithValidation("Enter Batch (Year of Joining: YYYY)", 3, ValidationUtils::isValidBatch,ValidationUtils.getBatchErrorMessage());
            if (batch == null) return false;

            int year = ValidationUtils.promptIntWithValidation("Enter Year of The Student (year > 0 and year <= 4)", 3, ValidationUtils::isValidYear,ValidationUtils.getYearErrorMessage());
            if (year == -1) return false;

            newUser = new Student(userId, username, password, type, contactInfo, batch, year);
        } else if (type.equalsIgnoreCase("faculty")) {
            String department = ValidationUtils.promptStringWithValidation("Enter Department", 3, ValidationUtils::isValidDepartment,ValidationUtils.getDepartmentErrorMessage());
            if (department == null) return false;

            newUser = new Faculty(userId, username, password, type, contactInfo, department);
        }

        boolean otpSent = otpHandler.initiateOTP(contactInfo);
        if (!otpSent) {
            System.out.println(ANSI_RED + "Failed to send OTP to " + contactInfo + ". Please enter your email again." + ANSI_RESET);
            contactInfo = ValidationUtils.promptStringWithValidation("Enter Contact Info (Email)", 3, ValidationUtils::isValidEmail,ValidationUtils.getEmailErrorMessage());
            newUser.setContactInfo(contactInfo);
            otpSent = otpHandler.initiateOTP(contactInfo);

            if (!otpSent) {
                System.out.println(ANSI_RED + "Failed to send OTP again. Please restart the sign-up process." + ANSI_RESET);
                return false;
            }
        } else {
            int prompt;
            System.out.println(ANSI_GREEN + "We have sent an email to your address. Did you receive it?" + ANSI_RESET);
            System.out.print("Received email? (Yes-1,No-0): ");
            prompt = scanner.nextInt();
            if (prompt == 0) {
                scanner.nextLine();
                contactInfo = ValidationUtils.promptStringWithValidation("Enter Contact Info (Email)", 3, ValidationUtils::isValidEmail,ValidationUtils.getEmailErrorMessage());
                newUser.setContactInfo(contactInfo);
                otpSent = otpHandler.initiateOTP(contactInfo);

                if (!otpSent) {
                    System.out.println(ANSI_RED + "Failed to send OTP again. Please restart the sign-up process." + ANSI_RESET);
                    return false;
                } else {
                    String otp = otpHandler.verifyOTP(scanner);
                    if (otp == null) {
                        return false;
                    } else {
                        writeToCSV(newUser);
                        return true;
                    }
                }
            } else {
                scanner.nextLine();
                String otp = otpHandler.verifyOTP(scanner);
                if (otp == null) {
                    return false;
                } else {
                    writeToCSV(newUser);
                    return true;
                }
            }
        }
        return false;
    }

    private void writeToCSV(LibraryUser user) {
        try {
            String filepath = "Databases/Student.csv";
            CSVWriter.writeObjectsToCSV(filepath, Collections.singletonList(user), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUserType(Scanner scanner) {
        String type;
        int attempt = 3;
        while (attempt > 0) {
            System.out.print("Enter User Type (student/faculty): ");
            type = scanner.nextLine().toLowerCase();
            if (type.equalsIgnoreCase("student") || type.equalsIgnoreCase("faculty")) {
                return type;
            }
            attempt--;
            System.out.println(ANSI_RED + "Invalid user type. Please enter either 'student' or 'faculty'." + ANSI_RESET);
            if (attempt == 0) {
                System.out.println(ANSI_RED + "Maximum attempts reached due to incorrect input. User registration has been terminated." + ANSI_RESET);
                return null;
            }
        }
        return null;
    }
}
