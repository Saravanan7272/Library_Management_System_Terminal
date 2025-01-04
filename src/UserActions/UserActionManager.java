package UserActions;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import InputValidationUtils.ValidationUtils;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import Users.*;
import java.util.List;
import java.util.Scanner;

public class UserActionManager {
    ManageBooksDetails manageBooksDetails=new ManageBooksDetails();
    private void handleUserAction(Scanner scanner, String userId, UserActionService userActionService, UserActionValidator userActionValidator) {
        try {
            // Load user records for validation
            userActionValidator.loadBorrowedBooks(userId);
            userActionValidator.loadReservations(userId);
            userActionValidator.loadCartCollections(userId);

            boolean canBorrow = userActionValidator.canBorrowBook(userId);
            boolean canReserve = userActionValidator.canReserveBook(userId);
            boolean canAddToCart = userActionValidator.canAddToCart(userId);

            // Display limitations
            displayLimitations(canBorrow, canReserve, canAddToCart);


            if (canBorrow || canReserve || canAddToCart) {
                System.out.println("\nHere are the actions you are eligible for:");
                displayEligibleActions(canBorrow, canReserve, canAddToCart);

                if (confirmAction(scanner)) {
                    String actionChoice = getUserActionChoice(scanner, canBorrow, canReserve, canAddToCart);
                    String isbn = ValidationUtils.promptStringWithValidation("Enter the Book ISBN for your chosen action", 2, ValidationUtils::isValidIsbn,ValidationUtils.getIsbnErrorMessage());
                    if (isbn == null) return;

                    Book currentBook = new SearchBooks().curentBook(isbn);
                    if (currentBook == null) {
                        System.out.println("Book with ISBN ID " + isbn + " does not exist. Please choose another book.");
                        return;
                    }

                    // Confirm with the user after showing book details
                    if (!confirmBookDetails(scanner, currentBook)) {
                        System.out.println("Action canceled. Returning to the main menu.");
                        return;
                    }

                    // Perform the action based on user choice
                    performSelectedAction(actionChoice, userId, isbn, currentBook, canBorrow, canReserve, canAddToCart, userActionService);
                } else {
                    System.out.println("No action selected. Exiting.");
                }
            } else {
                System.out.println("\u001B[31m" + "Unfortunately, you do not have any eligible actions available at this time." + "\u001B[0m");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayLimitations(boolean canBorrow, boolean canReserve, boolean canAddToCart) {
        if (!canBorrow) {
            System.out.println("\u001B[31m" + "Alert: You have reached the maximum limit of borrowed books.\n" + "\u001B[0m");
        }
        if (!canReserve) {
            System.out.println("\u001B[31m" + "Alert: You have reached the maximum limit of reservations.\n" + "\u001B[0m");
        }
        if (!canAddToCart) {
            System.out.println("\u001B[31m" + "Alert: You have reached the maximum limit of cart collections.\n" + "\u001B[0m");
        }
    }

    private void displayEligibleActions(boolean canBorrow, boolean canReserve, boolean canAddToCart) {
        if (canBorrow) System.out.println("\u001B[36m" + "1. Borrow a book" + "\u001B[0m");
        if (canReserve) System.out.println("\u001B[35m" + "2. Reserve a book" + "\u001B[0m");
        if (canAddToCart) System.out.println("\u001B[34m" + "3. Add a book to cart" + "\u001B[0m");
        if (!canBorrow && !canReserve && !canAddToCart) {
            System.out.println("\u001B[31m" + "No actions available." + "\u001B[0m");
        }
    }
    private String getUserActionChoice(Scanner scanner, boolean canBorrow, boolean canReserve, boolean canAddToCart) {
        int maxAttempts = 3;
        String choice = "";
        String validChoices = "";
        if (canBorrow) validChoices += "1";  // Borrow
        if (canReserve) validChoices += "2";  // Reserve
        if (canAddToCart) validChoices += "3";  // Add to cart

        for (int attemptCount = 1; attemptCount <= maxAttempts; attemptCount++) {
            System.out.print("Please select the action by entering the corresponding number: ");
            choice = scanner.nextLine();

            if (validChoices.contains(choice)) {
                return choice;
            } else {
                System.out.println("\u001B[31m" + "Error: Invalid choice. Try again." + "\u001B[0m");
            }
        }
        System.out.println("\u001B[31m" + "Maximum attempts reached. Returning to the previous menu." + "\u001B[0m");
        return "";
    }

    private boolean confirmAction(Scanner scanner) {
        System.out.print("\nWould you like to proceed with one of the eligible actions? (yes:1/no:0): ");
        String proceed = scanner.nextLine().trim();
        return proceed.equalsIgnoreCase("1");
    }

    private boolean confirmBookDetails(Scanner scanner, Book currentBook) {
        System.out.println("\n\u001B[1;34mSelected Book Details:\u001B[0m");
        System.out.println("\u001B[36mTitle: \u001B[0m" + currentBook.getTitle());
        System.out.println("\u001B[35mAuthor: \u001B[0m" + currentBook.getAuthor());
        System.out.println("\u001B[33mPublisher: \u001B[0m" + currentBook.getPublisher());
        System.out.println("\u001B[32mISBN: \u001B[0m" + currentBook.getIsbn());
        System.out.println("\u001B[34mRow Name: \u001B[0m" + currentBook.getRowName());
        System.out.println("\u001B[31mRack No: \u001B[0m" + currentBook.getRackNo());
        System.out.println("\u001B[36mAvailable Quantity: \u001B[0m" + currentBook.getAvailableQuantity());
        System.out.print("\n\u001B[1;33mWould you like to proceed with this book? (yes:1/no:0): \u001B[0m");
        String confirmation = scanner.nextLine().trim();
        return confirmation.equals("1");
    }


    private void performSelectedAction(String actionChoice, String userId, String isbn, Book currentBook,
                                       boolean canBorrow, boolean canReserve, boolean canAddToCart, UserActionService userActionService) throws Exception {
        switch (actionChoice) {
            case "1":
                if (canBorrow && handleBookAvailability("borrow", isbn, currentBook)) {
                   currentBook.setNoOfborrowCount(currentBook.getNoOfborrowCount()+1);
                    manageBooksDetails.updateBookDetails(currentBook);
                    userActionService.createBorrowedBookRecord(userId, isbn);
                    System.out.println("Borrowed book successfully.");
                }
                break;

            case "2":
                if (canReserve && handleBookAvailability("reserve", isbn, currentBook)) {
                    userActionService.createReservationRecord(userId, isbn);
                    System.out.println("Reserved book successfully.");
                }
                break;

            case "3":
                if (canAddToCart) {
                    userActionService.createCartRecord(userId, isbn);
                    System.out.println("Added book to cart successfully.");
                } else {
                    System.out.println("\u001B[31m" + "Adding to cart is not available. Please select a valid action." + "\u001B[0m");
                }
                break;

            default:
                System.out.println("\u001B[31m" + "Invalid choice. Please select a valid action." + "\u001B[0m");
        }
    }

    private boolean handleBookAvailability(String actionType, String isbn, Book currentBook) throws Exception {
        if (currentBook.getAvailableQuantity() <= 0) {
            System.out.println("Book with ISBN ID " + isbn + " is currently not available for " + actionType + ".");
            return false;
        } else {
            currentBook.setAvailableQuantity(currentBook.getAvailableQuantity() - 1);
            new ManageBooksDetails().updateBookDetails(currentBook);
            return true;
        }
    }


    public void Renewal(Scanner scanner,String userId) throws Exception {
        List<BorrowedBooks> borrowedBooksList = CSVReader.readCSV("Databases/BorrowedBooks.csv", BorrowedBooks.class);
        BorrowedBooksService service = new BorrowedBooksService(borrowedBooksList);

        //String userId = ValidationUtils.promptStringWithValidation("Enter User ID for renewal (7 digits)", 2, ValidationUtils::isValidUserId,ValidationUtils.getUserIdErrorMessage());
        if (userId == null) return;

        String bookId = ValidationUtils.promptStringWithValidation("Enter Book ID for renewal (ISBN)", 2, ValidationUtils::isValidIsbn,ValidationUtils.getIsbnErrorMessage());
        if (bookId == null) return;

        if (!new SearchBooks().checkIfBookIdExists(bookId)) {
            System.out.println("Book with ISBN ID " + bookId + " does not exist. Cannot proceed with renewal.");
            return;
        }

        service.updateRenewalDate(userId, bookId);
        CSVWriter.writeObjectsToCSV("Databases/BorrowedBooks.csv", borrowedBooksList, 1);
    }

    // Main method for the user to select action
    public void UserActionManager(Scanner scanner,String userId) {
        UserActionService userActionService = new UserActionService();
        UserActionValidator userActionValidator = new UserActionValidator();

        handleUserAction(scanner, userId, userActionService, userActionValidator);
    }
}
