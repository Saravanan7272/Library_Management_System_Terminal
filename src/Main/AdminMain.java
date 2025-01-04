package Main;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import com.sun.net.httpserver.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.List;
import java.util.Scanner;


public class AdminMain {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";

    public static void start(User currentUser) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("ADMIN PATH");
            System.out.println("1) User action");
            System.out.println("2) Book action");
            System.out.println("3) SendMail");
            System.out.println("4) Others");
            System.out.println("5) Exit");
            System.out.print(ANSI_GREEN + "Enter your choice: " + ANSI_RESET);

            String choice = scanner.nextLine();

            if (choice.equals("5")) break;


            switch (choice) {
                case "1":
                    userAction(scanner);
                    break;
                case "2":
                    bookAction(scanner);
                    break;
                case "3":
                    mailAction(scanner);
                    break;
                case "4":
                    othersAction(scanner);
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid choice. Try again." + ANSI_RESET);
            }
            line();
        }
        scanner.close();
    }

    public static void userAction(Scanner scanner) throws Exception {
        while (true) {
            System.out.println("USER ACTION");
            System.out.println("1) Add User");
            System.out.println("2) Update User");
            System.out.println("3) Delete User");
            System.out.println("4) Search User");
            System.out.println("5) Show Blocked Users");
            System.out.println("6) ReturnBook");
            System.out.println("7) Add Fine");
            System.out.println("8) Exit");
            System.out.print(ANSI_GREEN + "Enter your choice: " + ANSI_RESET);

            String choice = scanner.nextLine();
            if (choice.equals("8")) break;


            switch (choice) {
                case "1":
                    System.out.println("Adding User...");
                    if(SecurityTokenCheck.handleAdminSecurityToken(0)){
                        LibraryUserManager userManager1 = new LibraryUserManager();
                        userManager1.addNewUser(scanner);
                    }else{
                        System.out.println("Access denied: Incorrect security key.");
                    }

                    break;
                case "2":
                    System.out.println("Updating User...");
                    if(SecurityTokenCheck.handleAdminSecurityToken(0)) {
                        LibraryUserManager userManager2 = new LibraryUserManager();
                        userManager2.updateUser(scanner);
                    }else{
                        System.out.println("Access denied: Incorrect security key.");
                    }

                    break;
                case "3":
                    System.out.println("Deleting User...");
                    if(SecurityTokenCheck.handleAdminSecurityToken(0)){
                    LibraryUserManager userManager3 = new LibraryUserManager();
                    userManager3.removeUser(scanner);
                    }else{
                        System.out.println("Access denied: Incorrect security key.");
                    }
                    break;
                case "4":
                    System.out.println("Searching User...");
                    InitiateSearchUserFunctions.start(scanner);
                    break;
                case "5":
                    BlockedUser.printBlockedStageUsers();
                    break;
                case "6":
                    System.out.println("Returning Book....");
                    List<BorrowedBooks> borrowedBooksList2 = CSVReader.readCSV("Databases/BorrowedBooks.csv", BorrowedBooks.class);
                    BorrowedBooksService service2 = new BorrowedBooksService(borrowedBooksList2);
                    service2.returnBook();
                    break;
                case "7":
                    List<BorrowedBooks> borrowedBooksList3 = CSVReader.readCSV("Databases/BorrowedBooks.csv", BorrowedBooks.class);
                    BorrowedBooksService service3 = new BorrowedBooksService(borrowedBooksList3);
                    service3.applyFines();
                    CSVWriter.writeObjectsToCSV("Databases/BorrowedBooks.csv",borrowedBooksList3,1);
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid choice. Try again." + ANSI_RESET);
            }
            line();
        }
    }

    public static void bookAction(Scanner scanner) throws Exception {
        while (true) {
            System.out.println("BOOK ACTION");
            System.out.println("1) Add Book");
            System.out.println("2) Update Book");
            System.out.println("3) Remove Book");
            System.out.println("4) Search");
            System.out.println("5) View Borrowed");
            System.out.println("6) View Reserved");
            System.out.println("7) View CartCollection");
            System.out.println("8) Exit");
            System.out.print(ANSI_GREEN + "Enter your choice: " + ANSI_RESET);

            String choice = scanner.nextLine();

            if (choice.equals("8")) break;


            switch (choice) {
                case "1":
                    System.out.println("Adding Book...");
                    if (SecurityTokenCheck.handleAdminSecurityToken(0)) {
                        LibraryBookManager libraryBookManager1 = new LibraryBookManager();
                        libraryBookManager1.addNewBook();
                    } else {
                        System.out.println("Access denied: Incorrect security key.");
                    }
                    break;
                case "2":
                    System.out.println("Updating Book...");
                    if (SecurityTokenCheck.handleAdminSecurityToken(0)) {
                        LibraryBookManager libraryBookManager2 = new LibraryBookManager();
                        libraryBookManager2.updateBook();
                    } else {
                        System.out.println("Access denied: Incorrect security key.");
                    }
                    break;
                case "3":
                    System.out.println("Removing Book...");
                    if (SecurityTokenCheck.handleAdminSecurityToken(0)) {
                        LibraryBookManager libraryBookManager3 = new LibraryBookManager();
                        libraryBookManager3.removeBook();
                    } else {
                        System.out.println("Access denied: Incorrect security key.");
                    }

                    break;
                case "4":
                    System.out.println("Searching Book...");
                    InitiateSearchBookFunctions.start(scanner);
                    break;
                case "5":
                    System.out.println("Viewing Borrowed Books...");
                    LibraryAccountSummary BorrowedSummary = new LibraryAccountSummary();
                    BorrowedSummary.showBorrowedBooks();
                    break;
                case "6":
                    System.out.println("Viewing Reservation...");
                    LibraryAccountSummary ReservationSummary = new LibraryAccountSummary();
                    ReservationSummary.showReservations();
                    break;
                case "7":
                    System.out.println("Viewing CartCollection Books...");
                    LibraryAccountSummary CartCollectionSummary = new LibraryAccountSummary();
                    CartCollectionSummary.showCartCollections();
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid choice. Try again." + ANSI_RESET);
            }
            line();
        }
    }

    public static void mailAction(Scanner scanner) {
        while (true) {
            System.out.println("MAIL");
            System.out.println("1) SendMailToRenew");
            System.out.println("2) SendMailToReturn");
            System.out.println("3) SendMailToOverdue");
            System.out.println("4) SendMailTo BlockedStage Users");
            System.out.println("5) Exit");
            System.out.print(ANSI_GREEN + "Enter your choice: " + ANSI_RESET);

            String choice = scanner.nextLine();

            if (choice.equals("5")) break;


            switch (choice) {
                case "1":
                    System.out.println("Processing Renewal Mail...");
                    try {
                        // Load BorrowedBooks list from CSV
                        List<BorrowedBooks> borrowedBooksList = CSVReader.readCSV("Databases/BorrowedBooks.csv", BorrowedBooks.class);
                        // Create a notification service instance
                        UserNotificationService notificationService = new UserNotificationService(borrowedBooksList);

                        // Get users for renewal alert Books and send mail
                        List<BorrowedBooks> renewalAlertBooks = notificationService.getBooksForRenewalAlert();
                        System.out.println(renewalAlertBooks);
                        notificationService.getBookNamesByBookIds(renewalAlertBooks);
                        notificationService.getUserIds(renewalAlertBooks);
                        notificationService.sendAlert(renewalAlertBooks, "renewal");
                        System.out.println("Renewal alerts sent to users with borrowed books due for renewal.");

                    } catch (Exception e) {
                        e.printStackTrace(); // Print stack trace for debugging purposes
                    }

                    break;
                case "2":
                    System.out.println("Processing Return Mail...");
                    try {
                        // Load BorrowedBooks list from CSV
                        List<BorrowedBooks> borrowedBooksList = CSVReader.readCSV("Databases/BorrowedBooks.csv", BorrowedBooks.class);
                        // Create a notification service instance
                        UserNotificationService notificationService = new UserNotificationService(borrowedBooksList);
                        List<BorrowedBooks> returnAlertBooks = notificationService.getBooksForReturnAlert();
                        System.out.println(returnAlertBooks);
                        notificationService.getBookNamesByBookIds(returnAlertBooks);
                        notificationService.getUserIds(returnAlertBooks);
                        notificationService.sendAlert(returnAlertBooks, "return");
                        System.out.println("Renewal alerts sent to users with borrowed books due for renewal.");

                    } catch (Exception e) {
                        e.printStackTrace(); // Print stack trace for debugging purposes
                    }
                    break;
                case "3":
                    System.out.println("Processing Overdue Mail...");
                    try {
                        // Load BorrowedBooks list from CSV
                        List<BorrowedBooks> borrowedBooksList = CSVReader.readCSV("Databases/BorrowedBooks.csv", BorrowedBooks.class);
                        // Create a notification service instance
                        UserNotificationService notificationService = new UserNotificationService(borrowedBooksList);
                        List<BorrowedBooks> overdueAlertBooks = notificationService.getBooksForOverdueAlert();
                        System.out.println(overdueAlertBooks);
                        notificationService.getBookNamesByBookIds(overdueAlertBooks);
                        notificationService.getUserIds(overdueAlertBooks);
                        notificationService.sendAlert(overdueAlertBooks, "Overdue");
                        System.out.println("Overdue alerts sent to users with borrowed books due for return.");
                    } catch (Exception e) {
                        e.printStackTrace(); // Print stack trace for debugging purposes
                    }
                    break;
                    case "4":
                    System.out.println("Processing BlockedStage Users...");
                    try {
                        List<BorrowedBooks> borrowedBooksList = CSVReader.readCSV("Databases/BorrowedBooks.csv", BorrowedBooks.class);

                        UserNotificationService notificationService = new UserNotificationService(borrowedBooksList);
                        int res=notificationService.sendBlockedUserAlert();
                        if(res==0){System.out.println("Blocked Stage alerts sent to users unsuccessfully.");}
                        else{
                        System.out.println("Blocked Stage alerts sent to users successfully."); }
                    } catch (Exception e) {
                        System.err.println("Error processing blocked stage users.");
                        e.printStackTrace();  // Optional: Log exception details for debugging
                    }
                    break;

                default:
                    System.out.println(ANSI_RED + "Invalid choice. Try again." + ANSI_RESET);
            }
            line();
        }
    }

    public static void othersAction(Scanner scanner) throws Exception {
        while (true) {
            System.out.println("OTHERS");
            System.out.println("1) View Requested Books");
            System.out.println("2) View Feedback");
            System.out.println("3) Exit");
            System.out.print(ANSI_GREEN + "Enter your choice: " + ANSI_RESET);

            String choice = scanner.nextLine();

            if (choice.equals("3")) break;


            switch (choice) {
                case "1":
                    System.out.println("Viewing Requested Books...");
                    BookRequest.displayRequestBooks();
                    break;
                case "2":
                    System.out.println("Viewing Feedback...");
                    Feedback.displayFeedbacks();
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid choice. Try again." + ANSI_RESET);
            }
            line();
        }
    }

    public static void line() {
        System.out.println("-".repeat(50)); // Adjust the length as needed
    }
}


