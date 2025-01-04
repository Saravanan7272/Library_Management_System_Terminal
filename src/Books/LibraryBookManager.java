package Books;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Scanner;

import InputValidationUtils.ValidationUtils;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import java.util.function.Consumer;

import static java.lang.System.exit;

public class LibraryBookManager {

    private final ManageBooksDetails bookManager = new ManageBooksDetails();
    private final SearchBooks searchBooks = new SearchBooks();
    private final Scanner scanner = new Scanner(System.in);

    // Method to add a new book
    public void addNewBook() {
        System.out.println("Adding New Book... Please enter all the required details.");
        try {
            // Use the ValidationUtils class for validation
            String isbn = ValidationUtils.promptStringWithValidation("ISBN", 2, ValidationUtils::isValidIsbn,ValidationUtils.getIsbnErrorMessage());
            if(isbn==null) return;

            SearchBooks searchBooks=new SearchBooks();
            if(searchBooks.checkIfBookIdExists(isbn)){
                System.out.println("Book with This ISBN ID " + isbn + " already exists.");
                return ;}

            String title = ValidationUtils.promptStringWithValidation("Title", 2, ValidationUtils::isValidTitle,ValidationUtils.getTitleErrorMessage());
            if(title==null)return;

            String author = ValidationUtils.promptStringWithValidation("Author", 2, ValidationUtils::isValidAuthor,ValidationUtils.getAuthorErrorMessage());
            if(author==null)return;

            String publisher = ValidationUtils.promptStringWithValidation("Publisher", 2, ValidationUtils::isValidPublisher,ValidationUtils.getPublisherErrorMessage());
            if(publisher==null)return;

            String group = ValidationUtils.promptStringWithValidation("Book Group", 2, ValidationUtils::isValidBookGroup,ValidationUtils.getBookGroupErrorMessage());
            if(group==null)return;

            int totalQuantity = ValidationUtils.promptIntWithValidation("Total Quantity", 2, quantity -> quantity >= 0,ValidationUtils.getTotalQuantityErrorMessage());
            if(totalQuantity == -1)return;

            String rowName = ValidationUtils.promptStringWithValidation("Row Name (eg:Row 6)", 2, ValidationUtils::isValidRowName,ValidationUtils.getRowNameErrorMessage());
            if(rowName ==null)return;

            String rackNo = ValidationUtils.promptStringWithValidation("Rack Number (eg: Rack F)", 2, ValidationUtils::isValidRackNo,ValidationUtils.getRackNoErrorMessage());
            if(rackNo ==null)return;

            int noOfReservations = 0;

            // Create a new book object and add it to the book manager
            Book newBook = new Book(isbn, title, author, publisher, group, totalQuantity, totalQuantity, rowName, rackNo, noOfReservations);
            bookManager.addNewBook(newBook);
            System.out.println("New book added successfully.");

        } catch (Exception e) {
            System.out.println("Error adding new book: " + e.getMessage());
        }
    }

    public void displayBook(Book currentBook){
        System.out.println("\n\u001B[1;34mCurrent book details: \u001B[0m");
        System.out.println("\u001B[36mTitle: \u001B[0m" + currentBook.getTitle());
        System.out.println("\u001B[35mAuthor: \u001B[0m" + currentBook.getAuthor());
        System.out.println("\u001B[33mPublisher: \u001B[0m" + currentBook.getPublisher());
        System.out.println("\u001B[32mISBN: \u001B[0m" + currentBook.getIsbn());
        System.out.println("\u001B[34mRow Name: \u001B[0m" + currentBook.getRowName());
        System.out.println("\u001B[31mRack No: \u001B[0m" + currentBook.getRackNo());
        System.out.println("\u001B[36mAvailable Quantity: \u001B[0m" + currentBook.getAvailableQuantity());
    }
    public void updateBook() {
        String isbnToUpdate = ValidationUtils.promptStringWithValidation("Enter the ISBN of the book you wish to update", 2, ValidationUtils::isValidIsbn,ValidationUtils.getIsbnErrorMessage());
        if(isbnToUpdate==null) return;
        try {
            Book bookToUpdate = searchBooks.searchByIsbn(isbnToUpdate);

            if (bookToUpdate != null) {
                //System.out.println("Current book details: " + bookToUpdate);
                displayBook(bookToUpdate);
                updateBookFields(bookToUpdate);
                bookManager.updateBookDetails(bookToUpdate);
                System.out.println("Book details updated successfully.");
            } else {
                System.out.println("Book with ISBN " + isbnToUpdate + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating book: " + e.getMessage());
        }
    }

    // Helper method to update book fields
    private void updateBookFields(Book bookToUpdate) {
        System.out.println("Which field would you like to update?");
        System.out.println("1. Title\n2. Author\n3. Publisher\n4. Book Group\n5. Total Quantity");
        System.out.println("6. Available Quantity\n7. Row Name\n8. Rack Number\n9. Exit");

        String choice;
        System.out.print("Enter Choice:");
        while (!(choice = scanner.nextLine().trim()).equalsIgnoreCase("9")) {
            try {
                switch (choice) {
                    case "1":
                        updateField("Title", bookToUpdate::setTitle, ValidationUtils::isValidTitle,ValidationUtils.getTitleErrorMessage());
                        break;
                    case "2":
                        updateField("Author", bookToUpdate::setAuthor, ValidationUtils::isValidAuthor,ValidationUtils.getAuthorErrorMessage());
                        break;
                    case "3":
                        updateField("Publisher", bookToUpdate::setPublisher, ValidationUtils::isValidPublisher,ValidationUtils.getPublisherErrorMessage());
                        break;
                    case "4":
                        updateField("Book Group", bookToUpdate::setBookGroup, ValidationUtils::isValidBookGroup,ValidationUtils.getBookGroupErrorMessage());
                        break;
                    case "5":
                        updateQuantityField("Total Quantity", newQuantity -> {
                            if (newQuantity < bookToUpdate.getAvailableQuantity()) {
                                throw new IllegalArgumentException("Total Quantity cannot be less than Available Quantity.");
                            }
                            bookToUpdate.setTotalQuantity(newQuantity);
                        }, quantity -> quantity >= 0,ValidationUtils.getTotalQuantityErrorMessage());
                        break;

                    case "6":
                        updateQuantityField("Available Quantity", newQuantity -> {
                            if (newQuantity > bookToUpdate.getTotalQuantity()) {
                                throw new IllegalArgumentException("Available Quantity cannot exceed Total Quantity.");
                            }
                            bookToUpdate.setAvailableQuantity(newQuantity);
                        }, quantity -> quantity >= 0 && quantity <= bookToUpdate.getTotalQuantity(),ValidationUtils.getAvailableQuantityErrorMessage()); // Add the validation logic for Available Quantity
                        break;

                    case "7":
                        updateField("Row Name", bookToUpdate::setRowName, ValidationUtils::isValidRowName,ValidationUtils.getRowNameErrorMessage());
                        break;
                    case "8":
                        updateField("Rack Number", bookToUpdate::setRackNo, ValidationUtils::isValidRackNo,ValidationUtils.getRackNoErrorMessage());
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error updating field: " + e.getMessage());
            }
            System.out.println("Enter another field number to update or '9' to finish:");
        }
    }

    // Method to remove a book
    public void removeBook() {
        String isbn = ValidationUtils.promptStringWithValidation("Enter the ISBN of the book to remove", 2, ValidationUtils::isValidIsbn,ValidationUtils.getIsbnErrorMessage());

        try {
            Book bookToRemove = searchBooks.searchByIsbn(isbn);

            if (bookToRemove != null) {
                bookManager.removeBook(bookToRemove);
                System.out.println("Book removed successfully.");
            } else {
                System.out.println("Book with ISBN " + isbn + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
    }


    private void updateField(String fieldName, Consumer<String> setter, ValidationUtils.InputValidator<String> validator, String errorMessage) {
        while (true) {
            String newValue = ValidationUtils.promptStringWithValidation("Enter new " + fieldName, 2, validator,errorMessage);
            if (newValue == null || !validator.isValid(newValue)) {
                System.out.println(errorMessage);
                continue;
            }
            setter.accept(newValue);
            break;
        }
    }

    private void updateQuantityField(String fieldName, Consumer<Integer> setter, ValidationUtils.InputValidator<Integer> validator,String errorMessage) {
        while (true) {
            try {
                int quantity = ValidationUtils.promptIntWithValidation("Enter new " + fieldName, 2, validator,errorMessage);
                setter.accept(quantity);
                break;
            } catch (Exception e) {
                System.out.println("Invalid input for " + fieldName + ": " + e.getMessage());
            }
        }
    }

}
