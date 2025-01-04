package SearchQuerys;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class InitiateSearchBookFunctions {
    public static void start(Scanner scanner) {  // Accept the existing scanner as a parameter
        SearchBooks searchBooks = new SearchBooks();

        while (true) {
            System.out.println("1. Search by Title");
            System.out.println("2. Search by Author");
            System.out.println("3. Search by ISBN");
            System.out.println("4. Search by Publisher");
            System.out.println("5. Search by Book Group");
            System.out.println("6. Search by Row Name");
            System.out.println("7. Search by Rack Number");
            System.out.println("8. Search by Available Quantity");
            System.out.println("9. Search by Number of borrowCount");
            System.out.println("10. Search by Multiple Criteria");
            System.out.println("11. Exit:");

            System.out.print("Your choice: ");

            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("11")) {
                break;
            }

            switch (choice) {
                case "1":
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    List<Book> booksByTitle = searchBooks.searchByTitle(title);
                    displayResults(booksByTitle);
                    break;

                case "2":
                    System.out.print("Enter author name: ");
                    String author = scanner.nextLine();
                    List<Book> booksByAuthor = searchBooks.searchByAuthor(author);
                    displayResults(booksByAuthor);
                    break;

                case "3":
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    Book bookByIsbn = searchBooks.searchByIsbn(isbn);
                    if (bookByIsbn != null) {
                        System.out.println(Book.getHeader());
                        System.out.println("-".repeat(200));
                        System.out.println(bookByIsbn);
                        System.out.println("-".repeat(200));
                    } else {
                        System.out.println("No results found for the given ISBN.");
                    }
                    break;

                case "4":
                    System.out.print("Enter publisher name: ");
                    String publisher = scanner.nextLine();
                    List<Book> booksByPublisher = searchBooks.searchByPublisher(publisher);
                    displayResults(booksByPublisher);
                    break;

                case "5":
                    System.out.print("Enter book group: ");
                    String bookGroup = scanner.nextLine();
                    List<Book> booksByBookGroup = searchBooks.searchByBookGroup(bookGroup);
                    displayResults(booksByBookGroup);
                    break;

                case "6":
                    System.out.print("Enter row name: ");
                    String rowName = scanner.nextLine();
                    List<Book> booksByRowName = searchBooks.searchByRowName(rowName);
                    displayResults(booksByRowName);
                    break;

                case "7":
                    System.out.print("Enter rack number: ");
                    String rackNo = scanner.nextLine();
                    List<Book> booksByRackNo = searchBooks.searchByRackNo(rackNo);
                    displayResults(booksByRackNo);
                    break;

                case "8":
                    System.out.print("Enter available quantity: ");
                    int availableQuantity = Integer.parseInt(scanner.nextLine());
                    List<Book> booksByAvailableQuantity = searchBooks.searchByAvailableQuantity(availableQuantity);
                    displayResults(booksByAvailableQuantity);
                    break;

                case "9":
                    System.out.print("Enter number of reservations: ");
                    int noOfReservations = Integer.parseInt(scanner.nextLine());
                    List<Book> booksByNoOfReservations = searchBooks.searchByNoOfReservations(noOfReservations);
                    displayResults(booksByNoOfReservations);
                    break;

                case "10":
                    System.out.print("Enter title (or leave empty): ");
                    String multiTitle = scanner.nextLine();
                    System.out.print("Enter author (or leave empty): ");
                    String multiAuthor = scanner.nextLine();
                    System.out.print("Enter ISBN (or leave empty): ");
                    String multiIsbn = scanner.nextLine();
                    System.out.print("Enter publisher (or leave empty): ");
                    String multiPublisher = scanner.nextLine();
                    System.out.print("Enter book group (or leave empty): ");
                    String multiBookGroup = scanner.nextLine();

                    List<Book> booksByMultipleCriteria = searchBooks.searchBooks(
                            searchBooks.getBooks(),
                            multiTitle.isEmpty() ? null : multiTitle,
                            multiAuthor.isEmpty() ? null : multiAuthor,
                            multiIsbn.isEmpty() ? null : multiIsbn,
                            multiPublisher.isEmpty() ? null : multiPublisher,
                            multiBookGroup.isEmpty() ? null : multiBookGroup,
                            null, null, null, null);

                    displayResults(booksByMultipleCriteria);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void displayResults(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No results found.");
        } else {
            //books.forEach(System.out::println);
            System.out.println(Book.getHeader());
            System.out.println("-".repeat(100));
            for (Book book : books) {
                System.out.println(book);
                System.out.println("-".repeat(100));

            }
        }
    }
}
