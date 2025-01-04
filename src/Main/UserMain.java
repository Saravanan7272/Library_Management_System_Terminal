package Main;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserMain {

    public static void studentAction(Scanner scanner,LibraryUser currentMember) throws Exception {

        while (true) {
            System.out.println("STUDENT ACTION");
            System.out.println("1) Book Actions");
            System.out.println("2) Renew");
            System.out.println("3) Account Summary");
            System.out.println("4) Give Feedback");
            System.out.println("5) Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("5")) break;

            switch (choice) {
                case "1":
                    System.out.println("BOOK ACTIONS INCLUDES: " +
                            "\u001B[36m" + "Borrow Book" + "\u001B[0m, " +
                            "\u001B[35m" + "Reserve Book" + "\u001B[0m, " +
                            "\u001B[34m" + "Cart Book" + "\u001B[0m");
                    UserActionManager userActionManager = new UserActionManager();
                    userActionManager.UserActionManager(scanner,currentMember.getUserId());
                    break;
                case "2":
                    System.out.println("Renewing Book...");
                    UserActionManager userActionManagerForRenewal = new UserActionManager();
                    userActionManagerForRenewal.Renewal(scanner,currentMember.getUserId());
                    break;
                case "3":
                    System.out.println("Viewing Account Summary...");
                    UserActionHandler actionHandler1 = new UserActionHandler();
                    actionHandler1.viewAccountSummary(scanner,currentMember.getUserId());
                    break;
                case "4":
                    System.out.println("Giving Feedback...");
                    UserActionHandler actionHandler2 = new UserActionHandler();
                    actionHandler2.giveFeedback(currentMember.getUserId());
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void facultyAction(Scanner scanner,LibraryUser currentMember) throws Exception {
        while (true) {
            System.out.println("FACULTY ACTION");
            System.out.println("1) Book Actions");
            System.out.println("2) Renew");
            System.out.println("3) Account Summary");
            System.out.println("4) Give Feedback");
            System.out.println("5) Request Book");
            System.out.println("6) Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("6")) break;

            switch (choice) {
                case "1":
                    System.out.println("BOOK ACTIONS INCLUDES: " +
                            "\u001B[36m" + "Borrow Book" + "\u001B[0m, " +
                            "\u001B[35m" + "Reserve Book" + "\u001B[0m, " +
                            "\u001B[34m" + "Cart Book" + "\u001B[0m");
                    UserActionManager userActionManagerForCart = new UserActionManager();
                    userActionManagerForCart.UserActionManager(scanner,currentMember.getUserId());
                    break;
                case "2":
                    System.out.println("Renewing Book...");
                    UserActionManager userActionManagerForRenewal = new UserActionManager();
                    userActionManagerForRenewal.Renewal(scanner,currentMember.getUserId());
                    break;
                case "3":
                    System.out.println("Viewing Account Summary...");
                    UserActionHandler actionHandler1 = new UserActionHandler();
                    actionHandler1.viewAccountSummary(scanner,currentMember.getUserId());
                    break;
                case "4":
                    System.out.println("Giving Feedback...");
                    UserActionHandler actionHandler2 = new UserActionHandler();
                    actionHandler2.giveFeedback(currentMember.getUserId());
                    break;
                case "5":
                    System.out.println("Requesting Book...");
                    UserActionHandler actionHandler3 = new UserActionHandler();
                    actionHandler3.requestBook(currentMember.getUserId());
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
