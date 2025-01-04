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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
public class CartCollection {
    private String userId;  // User ID as a String
    private String bookId;  // Book ID as a String
    private String dateAdded; // Date added to the cart


    public CartCollection(){

    }

    public CartCollection(String userId, String bookId, String dateAdded) {
        this.userId = userId;
        this.bookId = bookId;
        this.dateAdded = dateAdded;
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

    public String getDateAdded() {
        return dateAdded;
    }
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
    @Override
    public String toString() {
        return String.format(
                "\u001B[32m%-15s\u001B[0m \u001B[34m%-25s\u001B[0m \u001B[35m%-20s\u001B[0m",
                userId, bookId, dateAdded
        );
    }
    public static void displayNullValues() {
        System.out.printf(
                "\u001B[32m%-15s\u001B[0m \u001B[34m%-25s\u001B[0m \u001B[35m%-20s\u001B[0m%n",
                "N/A","N/A","N/A");
    }


    // Static method to print table header with colors
    public static String getHeader() {
        return String.format(
                "\u001B[32m%-20s\u001B[0m \u001B[34m%-20s\u001B[0m \u001B[35m%-20s\u001B[0m",
                "UserID", "BookID", "DateAdded"
        );
    }


//    @Override
//    public String toString() {
//        return "CartCollection{" +
//                "userId='" + userId + '\'' +
//                ", bookId='" + bookId + '\'' +
//                ", dateAdded=" + dateAdded +
//                '}';
//    }

}

