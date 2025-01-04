package UserActions;
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
import java.util.Collections;

public class UserActionService {
    private static final String BORROWED_BOOKS_FILE_PATH = "Databases/BorrowedBooks.csv";
    private static final String RESERVATION_FILE_PATH = "Databases/Reservations.csv";
    private static final String CART_COLLECTION_FILE_PATH = "Databases/CartCollections.csv";

    // Method to create a BorrowedBooks record and write it to CSV
    public void createBorrowedBookRecord(String userId, String bookId) throws Exception {
        String checkOutDate = DateUtils.getCurrentDate();
        String renewalDate = DateUtils.getRenewalDate();
        String returnDate = DateUtils.getReturnDate();
        int renewalCount = 3;
        double fineAmount = 0.0;
        int renewedCount = 0;
        BorrowedBooks borrowedBook = new BorrowedBooks(userId, bookId, checkOutDate, renewalDate, returnDate, renewalCount,renewedCount, fineAmount);
        // Write the borrowed book record to CSV
        CSVWriter.writeObjectsToCSV(BORROWED_BOOKS_FILE_PATH, Collections.singletonList(borrowedBook), 0);
    }

    // Method to create a Reservation record and write it to CSV
    public void createReservationRecord(String userId, String bookId) throws Exception {
        String reservationDate = DateUtils.getCurrentDate();
        String pickupDeadline = DateUtils.getPickupDeadline();
        Reservation reservation = new Reservation(userId, bookId, reservationDate, pickupDeadline);
        CSVWriter.writeObjectsToCSV(RESERVATION_FILE_PATH, Collections.singletonList(reservation), 0);
    }

    // Method to create a CartCollection record and write it to CSV
    public void createCartRecord(String userId, String bookId) throws Exception {
        String dateAdded = DateUtils.getCurrentDate();
        CartCollection cartItem = new CartCollection(userId, bookId, dateAdded);
        // Write the cart collection record to CSV
        CSVWriter.writeObjectsToCSV(CART_COLLECTION_FILE_PATH, Collections.singletonList(cartItem), 0);
    }
}