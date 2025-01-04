package UserActions;

import java.util.List;
import java.util.Scanner;

public class ViewAccountSummary {

    private UserActionValidator userActionValidator;

    public ViewAccountSummary(UserActionValidator userActionValidator) {
        this.userActionValidator = userActionValidator;
    }

    // Method to display menu and get user choice
    public void displaySummary(String userId,Scanner scanner) throws Exception {
        //Scanner scanner = new Scanner(System.in);

        // Display options
        System.out.println("\n\u001B[1;34mChoose what you want to view:\u001B[0m");
        System.out.println("\u001B[36m"+"1. Borrowed Books"+"\u001B[0m");
        System.out.println("\u001B[35m"+"2. Reservations"+"\u001B[0m");
        System.out.println("\u001B[34m"+"3. Cart Collections"+"\u001B[0m");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();


        switch (choice) {
            case "1":
                displayBorrowedBooks(userId);
                break;
            case "2":
                displayReservations(userId);
                break;
            case "3":
                displayCartCollections(userId);
                break;
            default:
                System.out.println("Invalid choice. Please choose a valid option.");
        }

    }

    // Method to display borrowed books in a table format
    private void displayBorrowedBooks(String userId) throws Exception {
        List<BorrowedBooks> borrowedBooksList = userActionValidator.loadBorrowedBooks(userId);
        System.out.println(BorrowedBooks.getHeader());// Print table header
        System.out.println("-".repeat(100));
        if(!borrowedBooksList.isEmpty()) {
            for (BorrowedBooks book : borrowedBooksList) {
                System.out.println(book);
                System.out.println("-".repeat(100));
            }
            System.out.println("-".repeat(100));
        }else{
            BorrowedBooks.displayNullValues();
            System.out.println("-".repeat(100));
        }
    }

    // Method to display reservations in a table format
    private void displayReservations(String userId) throws Exception {
        List<Reservation> reservationList = userActionValidator.loadReservations(userId);
        System.out.println(Reservation.getHeader()); // Print table header
        System.out.println("-".repeat(100));
        if(!reservationList.isEmpty()) {
            for (Reservation reservation : reservationList) {
                System.out.println(reservation);
            }
            System.out.println("-".repeat(100));
        }else{
            Reservation.displayNullValues();
            System.out.println("-".repeat(100));
        }
    }

    // Method to display cart collections in a table format
    private void displayCartCollections(String userId) throws Exception {
        List<CartCollection> cartCollectionList = userActionValidator.loadCartCollections(userId);
        System.out.println(CartCollection.getHeader()); // Print table header
        System.out.println("-".repeat(100));
        if(!cartCollectionList.isEmpty()) {
            for (CartCollection cart : cartCollectionList) {
                System.out.println(cart);

            }
            System.out.println("-".repeat(100));
        }else{
            CartCollection.displayNullValues();
            System.out.println("-".repeat(100));
        }
    }

}