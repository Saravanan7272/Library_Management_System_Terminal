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
public class ManageBooksDetails {
    private static final String FILE_PATH = "Databases/Books.csv";

    // Method to load all books from the CSV file
    public List<Book> loadBooksFromCSV() throws Exception {
        return CSVReader.readCSV(FILE_PATH, Book.class);
    }

    public void writeBooksToCSV(List<Book> books) throws Exception {
        CSVWriter.writeObjectsToCSV(FILE_PATH, books, 1);
    }

    // Add a new book
// Add a new book to the CSV file if it doesn't already exist
    public void addNewBook(Book newBook) throws Exception {
        List<Book> books = loadBooksFromCSV();
        // Check if a book with the same ISBN already exists
        boolean bookExists = books.stream()
                .anyMatch(book -> book.getIsbn().equals(newBook.getIsbn()));

        if (bookExists) {
            throw new Exception("Error: A book with ISBN " + newBook.getIsbn() + " already exists.");
        }
        CSVWriter.writeObjectsToCSV(FILE_PATH, Collections.singletonList(newBook), 0);
    }


    // Remove a book by matching its ISBN
    public void removeBook(Book bookToRemove) throws Exception {
        List<Book> books = loadBooksFromCSV();

        // Check if the book to remove exists in the list
        boolean bookExists = books.stream()
                .anyMatch(book -> book.getIsbn().equals(bookToRemove.getIsbn()));

        if (!bookExists) {
            throw new Exception("Error: The book with ISBN " + bookToRemove.getIsbn() + " does not exist.");
        }

        // Filter out the book to be removed based on ISBN
        List<Book> updatedBooks = books.stream()
                .filter(book -> !book.getIsbn().equals(bookToRemove.getIsbn()))
                .collect(Collectors.toList());

        writeBooksToCSV(updatedBooks);
    }

    // Update the details of a book based on its ISBN
    public void updateBookDetails(Book updatedBook) throws Exception {

       // System.out.println(updatedBook);
        List<Book> books = loadBooksFromCSV();

        // Check if the book to update exists in the list
        boolean bookExists = books.stream()
                .anyMatch(book -> book.getIsbn().equals(updatedBook.getIsbn()));

        if (!bookExists) {
            throw new Exception("Error: Updating The book with ISBN " + updatedBook.getIsbn() + " does not exist.");
        }

        // Update the book details if it exists in the list
        List<Book> updatedBooks = books.stream()
                .map(book -> book.getIsbn().equals(updatedBook.getIsbn()) ? updatedBook : book)
                .collect(Collectors.toList());

        writeBooksToCSV(updatedBooks);
    }
}

