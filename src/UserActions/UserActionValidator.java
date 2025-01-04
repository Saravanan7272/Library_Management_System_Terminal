package UserActions;

import FileFunction.CSVReader;

import java.util.List;
import java.util.stream.Collectors;

public class UserActionValidator {
    private static final int MAX_BORROWED_BOOKS = 7;
    private static final int MAX_RESERVATIONS = 5;
    private static final int MAX_CART_COLLECTIONS = 5;

    private static final String BORROWED_BOOKS_FILE_PATH = "Databases/BorrowedBooks.csv";
    private static final String RESERVATION_FILE_PATH = "Databases/Reservations.csv";
    private static final String CART_COLLECTION_FILE_PATH = "Databases/CartCollections.csv";

    // Class attributes to hold the counts of borrowed books, reservations, and cart collections
    private int borrowedBooksCount;
    private int reservationsCount;
    private int cartCollectionsCount;

    // Method to load and filter borrowed books for the user, and update the count
    public  List<BorrowedBooks> loadBorrowedBooks(String userId) throws Exception {
        // Class attributes to hold the user's data
        List<BorrowedBooks> borrowedBooksList = CSVReader.readCSV(BORROWED_BOOKS_FILE_PATH, BorrowedBooks.class)
                .stream()
                .filter(book -> book.getUserId().equals(userId))
                .collect(Collectors.toList());
        borrowedBooksCount = borrowedBooksList.size();  // Update the count
        return borrowedBooksList;
    }

    // Method to load and filter reservations for the user, and update the count
    public List<Reservation> loadReservations(String userId) throws Exception {
        List<Reservation> reservationList = CSVReader.readCSV(RESERVATION_FILE_PATH, Reservation.class)
                .stream()
                .filter(reservation -> reservation.getUserId().equals(userId))
                .collect(Collectors.toList());
        reservationsCount = reservationList.size();  // Update the count
        return reservationList;
    }

    // Method to load and filter cart collections for the user, and update the count
    public List<CartCollection> loadCartCollections(String userId) throws Exception {
        List<CartCollection> cartCollectionList = CSVReader.readCSV(CART_COLLECTION_FILE_PATH, CartCollection.class)
                .stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .collect(Collectors.toList());
        cartCollectionsCount = cartCollectionList.size();  // Update the count
        return cartCollectionList;
    }

    public int getBorrowedBooksCount() {
        return borrowedBooksCount;
    }

    public int getReservationsCount() {
        return reservationsCount;
    }

    public int getCartCollectionsCount() {
        return cartCollectionsCount;
    }

    public boolean canBorrowBook(String userId) throws Exception {
        loadBorrowedBooks(userId);  // Load and filter borrowed books
        return borrowedBooksCount < MAX_BORROWED_BOOKS;  // Use the attribute directly
    }

    public boolean canReserveBook(String userId) throws Exception {
        loadReservations(userId);  // Load and filter reservations
        return reservationsCount < MAX_RESERVATIONS;  // Use the attribute directly
    }

    public boolean canAddToCart(String userId) throws Exception {
        loadCartCollections(userId);  // Load and filter cart collections
        return cartCollectionsCount < MAX_CART_COLLECTIONS;  // Use the attribute directly
    }
}