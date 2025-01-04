package Users;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import java.io.*;
import java.util.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
public class BlockedUser {

    private String userId;
    private String bookId;
    private double fineAmount;
   public BlockedUser(){

   }
    public BlockedUser(String userId, String bookId, double fineAmount) {
        this.userId = userId;
        this.bookId = bookId;
        this.fineAmount = fineAmount;
    }


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

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    @Override
    public String toString() {
        return "BlockedUser [userId=" + userId + ", bookId=" + bookId + ", fineAmount=" + fineAmount + "]";
    }

    public static List<BlockedUser> getBlockedStageUsers() throws Exception {
        List<BorrowedBooks> borrowedBooksList = null;
        try {
            borrowedBooksList = CSVReader.readCSV("Databases/BorrowedBooks.csv", BorrowedBooks.class);
        } catch (Exception e) {
            return null;
        }
        List<BlockedUser> blockedUsers = new ArrayList<>();
        for (BorrowedBooks book : borrowedBooksList) {
            if (book.getRenewalCount() == -2) {
                BlockedUser blockedUser = new BlockedUser(book.getUserId(), book.getBookId(), book.getFineAmount());
                blockedUsers.add(blockedUser);
            }
        }
        CSVWriter.writeObjectsToCSV("Databases/BlockedUsers.csv",blockedUsers,0);
        return blockedUsers;
    }
    public static List<BlockedUser> loadBlockedUsers(){
        List<BlockedUser> blockedUsersList = null;
        try {
            blockedUsersList = CSVReader.readCSV("Databases/BlockedUsers.csv", BlockedUser.class);
        } catch (Exception e) {
            return null;
        }
        return blockedUsersList;
    }

    public static void printBlockedStageUsers() throws Exception {
        List<BlockedUser> blockedUsers = getBlockedStageUsers();
        if (!blockedUsers.isEmpty()) {
            System.out.println("Blocked Users Details:");
            System.out.println("-".repeat(75));
            blockedUsers.forEach(System.out::println);
            System.out.println("-".repeat(75));
        } else {
            System.out.println("No blocked stage users found.");
        }
    }
    public static void main(String[] args) {
        try {
            // Call the printBlockedStageUsers() method to print the details of the blocked users
            BlockedUser.printBlockedStageUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
