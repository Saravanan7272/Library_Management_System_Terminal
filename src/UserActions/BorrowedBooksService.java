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
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class BorrowedBooksService {

    private List<BorrowedBooks> borrowedBooksList;

    public BorrowedBooksService(List<BorrowedBooks> borrowedBooksList) {
        this.borrowedBooksList = borrowedBooksList;
    }
    public void updateRenewalDate(String userId, String bookId) {
        BorrowedBooks book = findBorrowedBook(userId, bookId);

        if (book == null) {
            System.out.println("No book found for User ID: " + userId + " with Book ID: " + bookId);
            return; // Exit if no matching book is found
        }

        // Check if the book can still be renewed (i.e., renewal count > 0)
        if (book.getRenewalCount() >= -1) {
            LocalDate renewalDate = LocalDate.parse(book.getRenewalDate());
            LocalDate today = LocalDate.now();
            System.out.println("Renewal Date : " + renewalDate + " to " + today);
            // If today is the renewal date, update the renewal date and decrement the count
            if (today.equals(renewalDate)) {
                String newRenewalDate = DateUtils.getRenewalDate(); // Get new renewal date
                book.setRenewalDate(newRenewalDate);

                int newRenewalCount = book.getRenewalCount() - 1;
                book.setRenewalCount(newRenewalCount);
                LocalDate checkoutdate= LocalDate.parse(book.getCheckOutDate());
                Period period = Period.between(checkoutdate, today);
                int newRewedCount = period.getDays() / 7;
                book.setRenewedCount(newRewedCount);

                System.out.println("Renewal updated for User ID: " + userId
                        + ", New Renewal Date: " + newRenewalDate
                        + ", Remaining Renewals: " + newRenewalCount);
            } else {
                System.out.println("Renewal is not yet due. Current Renewal Date: " + book.getRenewalDate());
            }
        } else {
            System.out.println("No renewals left for User ID: " + userId + " and Book ID: " + bookId);
        }
    }

    // Method to apply fines to all borrowed books
    public void applyFines() {
        for (BorrowedBooks book : borrowedBooksList) {
            addFine(book.getUserId(), book.getBookId());
        }
    }

    // Method to calculate fines based on userId and bookId
    public void addFine(String userId, String bookId) {
        LocalDate today = LocalDate.now(); // Current date
        BorrowedBooks borrowedBook = findBorrowedBook(userId, bookId);

        if (borrowedBook != null) {
            LocalDate returnDate = LocalDate.parse(borrowedBook.getReturnDate());
            int totalRenewals = borrowedBook.getRenewalCount() + borrowedBook.getRenewedCount();

            // Only apply fines if the total number of renewals is less than 3
            if (totalRenewals < 3) {
                double fineAmount = 0.0;

                // Case 1: If the current date is before or on the return date
                if (today.isBefore(returnDate)) {
                    fineAmount = 10 * (3 - totalRenewals);
                }

                // Case 2: If the current date is after the return date
                else {
                    fineAmount = 25 * (3 - totalRenewals);
                }

                // Update the total fine amount in the borrowed book
                borrowedBook.setFineAmount(borrowedBook.getFineAmount() + fineAmount);
              //  System.out.println("Fine added for User ID: " + userId + " on Book ID: " + bookId + ". Total fine: ₹" + borrowedBook.getFineAmount());
                System.out.println("\u001B[32mFine added for User ID: \u001B[34m" + userId +
                        "\u001B[32m on Book ID: \u001B[34m" + bookId +
                        "\u001B[32m. Total fine: \u001B[31m₹" + borrowedBook.getFineAmount() + "\u001B[0m");

            }
//            else {
//                //System.out.println("No fine added. Maximum renewals reached for User ID: " + userId);
//            }
        } else {
            System.out.println("Book not found for User ID: " + userId + " with Book ID: " + bookId);
        }
    }

    // Method to find a borrowed book based on userId and bookId
    private BorrowedBooks findBorrowedBook(String userId, String bookId) {
        for (BorrowedBooks book : borrowedBooksList) {
            if (book.getUserId().equals(userId) && book.getBookId().equals(bookId)) {
                return book;
            }
        }
        return null; // Return null if no matching book is found
    }
    public void returnBook() {
        Scanner scanner = new Scanner(System.in);
        try {

            String userId = ValidationUtils.promptStringWithValidation("Enter User ID (7 digits)", 2, ValidationUtils::isValidUserId,ValidationUtils.getUserIdErrorMessage());
            if (userId == null) return ;

            String bookId = ValidationUtils.promptStringWithValidation("Enter Book ID (ISBN)", 2, ValidationUtils::isValidIsbn,ValidationUtils.getIsbnErrorMessage());
            if(bookId==null) return;

            boolean bookRemoved = removeBorrowedBook(userId, bookId);

            if (bookRemoved) {
                System.out.println("Book returned successfully for User ID: " + userId + " and Book ID: " + bookId);
                CSVWriter.writeObjectsToCSV("Databases/BorrowedBooks.csv", borrowedBooksList, 1);
            } else {
                System.out.println("Error: Book not found for User ID: " + userId + " and Book ID: " + bookId);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
        } catch (Exception e) {
            System.out.println("An error occurred while returning the book: " + e.getMessage());
        }
    }

    // Helper method to find and remove a book by userId and bookId
    private boolean removeBorrowedBook(String userId, String bookId) {
        Iterator<BorrowedBooks> iterator = borrowedBooksList.iterator();
        while (iterator.hasNext()) {
            BorrowedBooks book = iterator.next();
            if (book.getUserId().equals(userId) && book.getBookId().equals(bookId)) {
                iterator.remove();  // Safely remove using iterator
                return true;  // Book found and removed
            }
        }
        return false;  // Book not found
    }

    public void reduceFineAndUpdateRenewedCount(String userId, String bookId) {
        // Find the borrowed book by userId and bookId
        BorrowedBooks borrowedBook = findBorrowedBook(userId, bookId);

        if (borrowedBook != null) {
            LocalDate today = LocalDate.now();  // Get today's date
            LocalDate checkoutDate = LocalDate.parse(borrowedBook.getCheckOutDate());  // Get checkout date

            // Calculate the number of weeks between checkoutDate and today
            Period period = Period.between(checkoutDate, today);
            int weeksElapsed = period.getDays() / 7;

            // Display current fine amount
            double currentFine = borrowedBook.getFineAmount();
            System.out.println("Current Fine: ₹" + currentFine);

            // Reduce the fine based on weeks elapsed (example: ₹10 per week)
            double reducedFine = currentFine - (10 * weeksElapsed);
            if (reducedFine < 0) reducedFine = 0;  // Ensure fine doesn't go below 0

            // Update the fine amount
            borrowedBook.setFineAmount(reducedFine);

            // Update the renewed count based on the weeks elapsed
            int newRenewedCount = borrowedBook.getRenewedCount() + weeksElapsed;  // Increment by weeks elapsed
            borrowedBook.setRenewedCount(newRenewedCount);

            // Display the updated fine and renewed count
            System.out.println("Fine reduced to: ₹" + reducedFine);
            System.out.println("Updated Renewed Count: " + newRenewedCount);
        } else {
            System.out.println("Book not found for User ID: " + userId + " with Book ID: " + bookId);
        }
    }



//        public static void main(String[] args) throws Exception {
//
//
//            // Example userId and bookId
//            String userId = "2310123";
//            String bookId = "9781617294133";
//            List<BorrowedBooks> borrowedBooksList = CSVReader.readCSV("Databases/BorrowedBooks.csv", BorrowedBooks.class);
//            BorrowedBooksService service = new BorrowedBooksService(borrowedBooksList);
//
//            // Call the reduceFineAndUpdateRenewedCount method
//            service.reduceFineAndUpdateRenewedCount(userId, bookId);
//            CSVWriter.writeObjectsToCSV("Databases/BorrowedBooks.csv",borrowedBooksList,1);
//        }





}