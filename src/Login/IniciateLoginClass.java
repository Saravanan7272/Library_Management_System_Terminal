package Login;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import InputValidationUtils.ValidationUtils;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class IniciateLoginClass {
    private static final String ADMIN_FILE = "Databases/Admins.csv";
    private static final String FACULTY_FILE = "Databases/Faculty.csv";
    private static final String STUDENT_FILE = "Databases/Student.csv";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final int MAX_INVALID_INPUTS = 2;
    private static final int MAX_LOGIN_ATTEMPTS = 2;

    public static User currentUser;
    public static LibraryUser currentMember;
    private static List<LibraryUser> users = new ArrayList<>();
    private static List<Admin> admins = new ArrayList<>();
    private static LoginSystem loginSystem = new LoginSystem();
    private static int isLoggedIn = 0;

    public static boolean start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1) Login");
            System.out.println("2) Exit");
            System.out.print(ANSI_GREEN + "Enter your choice: " + ANSI_RESET);

            String choice = scanner.nextLine().trim();

            if (choice.equals("2")) {
                System.out.println("Exiting the system.");
                scanner.close();
                return false;
            } else if (choice.equals("1")) {
                isLoggedIn = 0;

                while (isLoggedIn == 0 || isLoggedIn == -1) {
                    System.out.println("1) Admin");
                    System.out.println("2) Member");
                    System.out.println("3) GoToMain");
                    System.out.print(ANSI_GREEN + "Enter your choice: " + ANSI_RESET);

                    String loginChoice = scanner.nextLine().trim();

                    if (loginChoice.equals("3")) {
                        break;
                    }

                    switch (loginChoice) {
                        case "1":
                            int inter_choice=IniciateLoginClass.conformAdminLogin(scanner);
                           // System.out.println(inter_choice);
                            if (inter_choice==-1){isLoggedIn=-1;
                                break;}
                            else if (inter_choice==-2){isLoggedIn=-2;
                                break;}
                            else{ isLoggedIn = adminPath(scanner);
                                break;}

                        case "2":
                            isLoggedIn = memberPath(scanner);
                            break;

                        default:
                            System.out.println(ANSI_RED + "Invalid choice. Try again." + ANSI_RESET);
                    }

                    if (isLoggedIn == 1) {
                        System.out.println("Successfully logged in. Welcome!");
                        return true;
                    } else if (isLoggedIn == 0) {
                        System.out.println("Login process not completed!");
                        scanner.close();
                        return false;
                    }
                }
            } else {
                System.out.println(ANSI_RED + "Invalid choice. Try again." + ANSI_RESET);
            }
        }
    }

    private static int conformAdminLogin(Scanner scanner) {
        System.out.println("1) If You Want to login");
        System.out.println("2) For GoTo UserType Menu");
        System.out.print("Enter Your Choice: ");

        String option = scanner.nextLine().trim();

        if (option.equals("2")) return -1;
        else if (option.equals("1")) return 1;
        System.out.println("Your choice is incorrect. Returning to Main Menu.");
        return -2;
    }

    private static int adminPath(Scanner scanner) {
       // System.out.println("Admin Login");

        int attempts = 0;
        if (!loadAdmins()) {
            return 0;
        }

        while (attempts < MAX_LOGIN_ATTEMPTS) {
            String userId = ValidationUtils.promptStringWithValidation("Enter User ID (7 digits)", 3, ValidationUtils::isValidUserId,ValidationUtils.getUserIdErrorMessage());
            if (userId == null) return -1;

            String password = ValidationUtils.promptStringWithValidation("Enter Password", 3, ValidationUtils::isValidPassword,ValidationUtils.getPasswordErrorMessage());
            if (password == null) return -1;
            Admin admin = findAdmin(userId);
            if (admin == null) {
                System.out.println(ANSI_RED + "User not found." + ANSI_RESET);
            } else if (!admin.getPassword().equals(password)) {
                System.out.println(ANSI_RED + "Credential failed." + ANSI_RESET);
            } else if (loginSystem.performLogin(admin, userId, password)) {
                currentUser = admin;
                //System.out.println("Admin logged in successfully.");
                return 1;
            } else {
                System.out.println(ANSI_RED + "Admin security token verification failed." + ANSI_RESET);
            }

            attempts++;
            if (attempts < MAX_LOGIN_ATTEMPTS) {
                System.out.println(ANSI_RED + "Please try again. Attempt " + (attempts + 1) + " of " + MAX_LOGIN_ATTEMPTS + "." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Maximum login attempts exceeded. Returning to main menu." + ANSI_RESET);
            }
        }
        return 0;
    }

    private static Admin findAdmin(String userId) {
        return admins.stream().filter(admin -> admin.getUserId().equals(userId)).findFirst().orElse(null);
    }

    private static boolean loadAdmins() {
        try {
            admins = CSVReader.readCSV(ADMIN_FILE, Admin.class);
            return true;
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error reading admin data. Please contact support." + ANSI_RESET);
            return false;
        }
    }

    private static int memberPath(Scanner scanner) {
        System.out.println("1) Faculty");
        System.out.println("2) Student");
        System.out.println("3) GoToMemberType");
        System.out.print(ANSI_GREEN + "Enter your choice: " + ANSI_RESET);
        // Read member type as a string and parse it
        String memberTypeStr = scanner.nextLine().trim();

         if (memberTypeStr.equals("3")) {
            return -1;  // Return to the previous menu
        }

        // Convert member type string to integer
        int memberType = parseMemberType(memberTypeStr);
        if (!loadMembers(memberType)) {
            return 0;  // Exit if loading members fails
        }

        int attempts = 0;
        while (attempts < MAX_LOGIN_ATTEMPTS) {

            String userId = ValidationUtils.promptStringWithValidation("Enter User ID (7 digits)", 3, ValidationUtils::isValidUserId,ValidationUtils.getUserIdErrorMessage());
            if (userId == null) return -1;


            if (isBlockedUser(userId)) {
                System.out.println("You are currently blocked and cannot log in.");
                return 0;
            }

            String password = ValidationUtils.promptStringWithValidation("Enter Password", 3, ValidationUtils::isValidPassword,ValidationUtils.getPasswordErrorMessage());
            if (password == null) return -1;

            // Find the user and attempt login
            LibraryUser user = findUser(userId, password);

            if (user != null) {
                // Create LogicUser as a LibraryMember
                LibraryMember LogicUser = new LibraryMember(user.getUserId(), user.getUsername(), user.getPassword());

                if (loginSystem.performLogin(LogicUser, userId, password)) {
                    currentMember = user;
                    System.out.println("Member logged in successfully.");
                    return 1;  // Login successful
                } else {
                    System.out.println(ANSI_RED + "Login failed. Invalid credentials." + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "User not found or invalid credentials." + ANSI_RESET);
            }

            attempts++;
            if (attempts < MAX_LOGIN_ATTEMPTS) {
                System.out.println(ANSI_RED + "Please try again. Attempt " + (attempts + 1) + " of " + MAX_LOGIN_ATTEMPTS + "." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Maximum login attempts exceeded. Returning to main menu." + ANSI_RESET);
            }
        }
        return 0;  // Login unsuccessful after max attempts
    }
    private static int parseMemberType(String memberTypeStr) {
        try {
            return Integer.parseInt(memberTypeStr);
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "Invalid input for member type. Please enter a valid option." + ANSI_RESET);
            return -1;
        }
    }

    private static LibraryUser findUser(String userId, String password) {
        for (LibraryUser user : users) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static boolean loadMembers(int memberType) {
        users.clear();
        try {
            if (memberType == 1) {
                List<Faculty> facultyList = CSVReader.readCSV(FACULTY_FILE, Faculty.class);
                users.addAll(facultyList);
            } else if (memberType == 2) {
                List<Student> studentList = CSVReader.readCSV(STUDENT_FILE, Student.class);
                users.addAll(studentList);
            } else {
                System.out.println(ANSI_RED + "Invalid member type." + ANSI_RESET);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Error reading member data. Please contact support." + ANSI_RESET);
            return false;
        }
    }

    private static boolean isBlockedUser(String userId) {
        List<BlockedUser> blockedUsers = BlockedUser.loadBlockedUsers();
        if (blockedUsers != null) {
            for (BlockedUser blockedUser : blockedUsers) {
                if (blockedUser.getUserId().equalsIgnoreCase(userId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
