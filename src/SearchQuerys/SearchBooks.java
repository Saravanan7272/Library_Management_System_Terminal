package SearchQuerys;
import Books.*;
import FileFunction.*;

import java.util.List;
import java.util.stream.Collectors;

public class SearchBooks {
    // Instance variable for file path
    private final String filePath = "Databases/Books.csv";
    private List<Book> books;

    // Constructor to load books from the CSV file
    public SearchBooks() {
        loadBooks();
    }

    // Method to load books from the CSV file
    private void loadBooks() {
        try {
            books = CSVReader.readCSV(filePath, Book.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading books data from CSV file.");
            books = List.of(); // Initialize to an empty list on error
        }
    }


    // Method to get all books
    public List<Book> getBooks() {
        return books;
    }

    // Static method to search books
    public static List<Book> searchBooks(List<Book> books, String title, String author, String isbn, String publisher, String bookGroup, String rowName, String rackNo, Integer availableQuantity, Integer noOfReservations) {
        return books.stream()
                .filter(book -> title == null || title.isEmpty() || book.getTitle().equalsIgnoreCase(title))
                .filter(book -> author == null || author.isEmpty() || book.getAuthor().equalsIgnoreCase(author))
                .filter(book -> isbn == null || isbn.isEmpty() || book.getIsbn().equalsIgnoreCase(isbn))
                .filter(book -> publisher == null || publisher.isEmpty() || book.getPublisher().equalsIgnoreCase(publisher))
                .filter(book -> bookGroup == null || bookGroup.isEmpty() || book.getBookGroup().equalsIgnoreCase(bookGroup))
                .filter(book -> rowName == null || rowName.isEmpty() || book.getRowName().equalsIgnoreCase(rowName))
                .filter(book -> rackNo == null || rackNo.isEmpty() || book.getRackNo().equalsIgnoreCase(rackNo))
                .filter(book -> availableQuantity == null || book.getAvailableQuantity() == availableQuantity)
                .filter(book -> noOfReservations == null || book.getNoOfborrowCount() == noOfReservations)
                .collect(Collectors.toList());
    }

    // Instance method to search by title
    public List<Book> searchByTitle(String title) {
        return searchBooks(books, title, null, null, null, null, null, null, null, null);
    }

    // Instance method to search by author
    public List<Book> searchByAuthor(String author) {
        return searchBooks(books, null, author, null, null, null, null, null, null, null);
    }

    // Instance method to search by ISBN
    public Book searchByIsbn(String isbn) {
        List<Book> results = searchBooks(books, null, null, isbn, null, null, null, null, null, null);
        return results.isEmpty() ? null : results.get(0);
    }

    // Instance method to search by publisher
    public List<Book> searchByPublisher(String publisher) {
        return searchBooks(books, null, null, null, publisher, null, null, null, null, null);
    }

    // Instance method to search by book group
    public List<Book> searchByBookGroup(String bookGroup) {
        return searchBooks(books, null, null, null, null, bookGroup, null, null, null, null);
    }

    // Instance method to search by row name
    public List<Book> searchByRowName(String rowName) {
        return searchBooks(books, null, null, null, null, null, rowName, null, null, null);
    }

    // Instance method to search by rack number
    public List<Book> searchByRackNo(String rackNo) {
        return searchBooks(books, null, null, null, null, null, null, rackNo, null, null);
    }

    // Instance method to search by available quantity
    public List<Book> searchByAvailableQuantity(int availableQuantity) {
        return searchBooks(books, null, null, null, null, null, null, null, availableQuantity, null);
    }

    // Instance method to search by number of reservations
    public List<Book> searchByNoOfReservations(int noOfReservations) {
        return searchBooks(books, null, null, null, null, null, null, null, null, noOfReservations);
    }

    // Instance method to search for book titles by a list of book IDs
    public List<String> searchBookTitleByBookIds(List<String> bookIds) {
        return bookIds.stream()
                .flatMap(bookId -> books.stream()
                        .filter(book -> book.getIsbn().equalsIgnoreCase(bookId)) // Assuming ISBN is used as the unique identifier
                        .map(Book::getTitle) // Map to book title
                )
                .collect(Collectors.toList());
    }

    public boolean checkIfBookIdExists(String bookid){
        for(Book book:books){
            if(book.getIsbn().equalsIgnoreCase(bookid)){
                return true;
            }
        }
        return false;
    }
    public Book curentBook(String bookid){
        for(Book book:books){
            if(book.getIsbn().equalsIgnoreCase(bookid)){
                return book;
            }
        }
        return null;
    }
}
