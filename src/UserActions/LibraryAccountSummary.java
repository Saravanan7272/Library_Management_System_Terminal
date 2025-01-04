package UserActions;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import java.time.LocalDate;
import java.util.List;


import java.util.List;
import java.util.stream.Collectors;

public class LibraryAccountSummary {
    private static final String BORROWED_BOOKS_FILE_PATH = "Databases/BorrowedBooks.csv";
    private static final String RESERVATION_FILE_PATH = "Databases/Reservations.csv";
    private static final String CART_COLLECTION_FILE_PATH = "Databases/CartCollections.csv";

    private int borrowedBooksCount;
    private int reservationsCount;
    private int cartCollectionsCount;

    public List<BorrowedBooks> viewBorrowedBooks() throws Exception {
        List<BorrowedBooks> borrowedBooksList = CSVReader.readCSV(BORROWED_BOOKS_FILE_PATH, BorrowedBooks.class)
                .stream()
                .map(book -> new BorrowedBooks(book.getUserId(), book.getBookId(), book.getCheckOutDate(), null, null, 0, 0, 0.0))
                .collect(Collectors.toList());
        borrowedBooksCount = borrowedBooksList.size();
        return borrowedBooksList;
    }

    public List<Reservation> viewReservations() throws Exception {
        List<Reservation> reservationList = CSVReader.readCSV(RESERVATION_FILE_PATH, Reservation.class)
                .stream()
                .map(reservation -> new Reservation(reservation.getUserId(), reservation.getBookId(), reservation.getReservationDate(), reservation.getPickupDeadline()))
                .collect(Collectors.toList());
        reservationsCount = reservationList.size();
        return reservationList;
    }

    public List<CartCollection> viewCartCollections() throws Exception {
        List<CartCollection> cartCollectionList = CSVReader.readCSV(CART_COLLECTION_FILE_PATH, CartCollection.class)
                .stream()
                .map(cart -> new CartCollection(cart.getUserId(), cart.getBookId(), cart.getDateAdded()))
                .collect(Collectors.toList());
        cartCollectionsCount = cartCollectionList.size();
        return cartCollectionList;
    }

    public void showBorrowedBooks() throws Exception {

        List<BorrowedBooks> borrowedBooksList = viewBorrowedBooks();
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║               Borrowed Books Details               ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println(BorrowedBooks.getHeaderForShowBorrowedBooks());
        System.out.println("-".repeat(50));
        for (BorrowedBooks borrowedBook : borrowedBooksList) {
            borrowedBook.showBorrowedDetails();
        }
    }

    public void showReservations() throws Exception {

        List<Reservation> reservationList = viewReservations();
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║              Reservations Details                  ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println(Reservation.getReservationHeader());
        System.out.println("-".repeat(50));
        for (Reservation reservation : reservationList) {
            reservation.showReservationDetails();
        }
    }

    public void showCartCollections() throws Exception {

        List<CartCollection> cartCollectionList = viewCartCollections();
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║              Cart Collection Details               ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println(CartCollection.getHeader());
        System.out.println("-".repeat(50));
        for (CartCollection cartCollection : cartCollectionList) {
            System.out.println(cartCollection);  // Using the custom display method
        }
    }
}


