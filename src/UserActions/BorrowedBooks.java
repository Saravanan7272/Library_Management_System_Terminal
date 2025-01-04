package UserActions;
import java.util.Arrays;

// StudentID|BookID|CheckOutDate|RenewalDate|ReturnDate|RenewalCount|FineAmount
public class BorrowedBooks {
    // Fields: StudentID|BookID|CheckOutDate|RenewalDate|ReturnDate|RenewalCount|FineAmount
    private String userId;      // User ID as a String
    private String bookId;      // Book ID as a String
    private String checkOutDate; // Check Out Date as a String
    private String renewalDate;   // Renewal Date as a String
    private String returnDate;    // Return Date as a String
    private int renewalCount;     // Renwals left
    private int renewedCount;        // Count of renewals
    private double fineAmount;     // Fine amount
    public BorrowedBooks() {

    }
    // Constructor
    public BorrowedBooks(String userId, String bookId, String checkOutDate, String renewalDate, String returnDate, int renewalCount,int renewedCount, double fineAmount) {
        this.userId = userId;
        this.bookId = bookId;
        this.checkOutDate = checkOutDate;
        this.renewalDate = renewalDate;
        this.returnDate = returnDate;
        this.renewalCount = renewalCount;
        this.renewedCount = renewedCount;
        this.fineAmount = fineAmount;
    }

    public void setUser(String user) {
        this.userId = user;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(String renewalDate) {
        this.renewalDate = renewalDate;
    }


    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getRenewalCount() {
        return renewalCount;
    }

    public void setRenewalCount(int renewalCount) {
        this.renewalCount = renewalCount;
    }
    public int getRenewedCount() {
        return renewedCount;
    }
    public void setRenewedCount(int renewedCount) {
        this.renewedCount = renewedCount;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    @Override
    public String toString() {
        return String.format(
                "\u001B[32m%-15s\u001B[0m \u001B[34m%-15s\u001B[0m \u001B[35m%-15s\u001B[0m \u001B[36m%-15s\u001B[0m \u001B[33m%-15s\u001B[0m \u001B[31m%-10.2f\u001B[0m",
                userId, bookId, checkOutDate, renewalDate, returnDate, fineAmount
        );
    }
    public static void displayNullValues() {
        System.out.printf("\u001B[32m%-15s\u001B[0m \u001B[34m%-15s\u001B[0m \u001B[35m%-15s\u001B[0m \u001B[36m%-15s\u001B[0m \u001B[33m%-15s\u001B[0m \u001B[31m%-10.2f\u001B[0m%n",
                        "N/A","N/A","N/A","N/A","N/A",0.0 );
    }



    // Custom display method that shows only specific data
    public void showBorrowedDetails() {
        System.out.printf(
                "\u001B[32m%-15s\u001B[0m \u001B[34m%-25s\u001B[0m \u001B[35m%-20s\u001B[0m\n",
                userId, bookId, checkOutDate
        );
    }

    public static String getHeaderForShowBorrowedBooks() {
        return String.format(
                "\u001B[32m%-20s\u001B[0m \u001B[34m%-20s\u001B[0m \u001B[35m%-20s\u001B[0m",
                "UserID", "BookID", "CheckOutDate"
        );
    }

    public static String getHeader() {
        return String.format(
                "\u001B[32m%-15s\u001B[0m \u001B[34m%-15s\u001B[0m \u001B[35m%-15s\u001B[0m \u001B[36m%-15s\u001B[0m \u001B[33m%-15s\u001B[0m \u001B[31m%-10s\u001B[0m",
                "UserID", "BookID", "CheckOutDate", "RenewalDate", "ReturnDate", "FineAmount"
        );
    }

//    public String toString() {
//        return "BorrowedBooks{" +
//                "userId='" + userId + '\'' +
//                ", bookId='" + bookId + '\'' +
//                ", checkOutDate='" + checkOutDate + '\'' +
//                ", renewalDate='" + renewalDate + '\'' +
//                ", returnDate='" + returnDate + '\'' +
//                ", renewalCount='" + renewalCount+'\'' +
//               ", renewedCount='" + renewedCount+'\'' +
//                ", fineAmount='" + fineAmount +'\''+
//                '}';
//    }

}