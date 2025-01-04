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
import java.util.Arrays;
public class Reservation {
    // Fields: StudentID|BookID|ReservationDate|PickupDeadline
    private String userId;          // User ID as a String
    private String bookId;          // Book ID as a String
    private String reservationDate;  // Reservation Date as a String
    private String pickupDeadline;   // Pickup Deadline as a String
    public Reservation(){

    }
    // Constructor
    public Reservation(String userId, String bookId, String reservationDate, String pickupDeadline) {
        this.userId = userId;
        this.bookId = bookId;
        this.reservationDate = reservationDate;
        this.pickupDeadline = pickupDeadline;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getPickupDeadline() {
        return pickupDeadline;
    }

    public void setPickupDeadline(String pickupDeadline) {
        this.pickupDeadline = pickupDeadline;
    }
    @Override
    public String toString() {
        return String.format(
                "\u001B[32m%-15s\u001B[0m \u001B[34m%-15s\u001B[0m \u001B[35m%-20s\u001B[0m \u001B[36m%-20s\u001B[0m",
                userId, bookId, reservationDate, pickupDeadline
        );
    }
    public static void displayNullValues() {
        System.out.printf(
                "\u001B[32m%-15s\u001B[0m \u001B[34m%-15s\u001B[0m \u001B[35m%-20s\u001B[0m \u001B[36m%-20s\u001B[0m%n",
                "N/A", "N/A", "N/A", "N/A");
    }


    // Static method to print table header with colors
    public static String getHeader() {
        return String.format(
                "\u001B[32m%-15s\u001B[0m \u001B[34m%-15s\u001B[0m \u001B[35m%-20s\u001B[0m \u001B[36m%-20s\u001B[0m",
                "UserID", "BookID", "ReservationDate", "PickupDeadline"
        );
    }

    // Custom display method that shows only specific data with colors
    public void showReservationDetails() {
        System.out.printf(
                "\u001B[32m%-15s\u001B[0m \u001B[34m%-25s\u001B[0m \u001B[35m%-20s\u001B[0m\n",
                userId, bookId, reservationDate
        );
    }

    // Static method to print table header with colors for specific data
    public static String getReservationHeader() {
        return String.format(
                "\u001B[32m%-20s\u001B[0m \u001B[34m%-20s\u001B[0m \u001B[35m%-20s\u001B[0m",
                "UserID", "BookID", "ReservationDate"
        );
    }

//    @Override
//    public String toString() {
//        return "Reservation{" +
//                "userId='" + userId + '\'' +
//                ", bookId='" + bookId + '\'' +
//                ", reservationDate='" + reservationDate + '\'' +
//                ", pickupDeadline='" + pickupDeadline + '\'' +
//                '}';
//    }

}
