//package Mail;
//
//import Books.*;
//import DateUtils.*;
//import FileFunction.*;
//import Login.*;
//import SearchQuerys.*;
//import UserActions.*;
//import Users.*;
//import java.io.*;
//import java.util.*;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.ArrayList;
//
//
//public class UserNotificationService {
//    private List<String> userEmails;
//    private List<String> bookNames;
//    private List<BorrowedBooks> borrowedBooksList;
//    public UserNotificationService( List<BorrowedBooks> borrowedBooksList) {
//        this.borrowedBooksList = borrowedBooksList;
//    }
//
//    // Method to retrieve books needing a renewal alert
//    public List<BorrowedBooks> getBooksForRenewalAlert() {
//        LocalDate today = LocalDate.now();
//        return borrowedBooksList.stream()
//                .filter(book -> !book.getRenewalDate().isEmpty())
//                .filter(book -> LocalDate.parse(book.getRenewalDate()).minusDays(1).equals(today))
//                .collect(Collectors.toList());
//    }
//
//    // Method to retrieve books needing a return alert
//    public List<BorrowedBooks> getBooksForReturnAlert() {
//        LocalDate today = LocalDate.now();
//        return borrowedBooksList.stream()
//                .filter(book -> !book.getReturnDate().isEmpty())
//                .filter(book -> LocalDate.parse(book.getReturnDate()).minusDays(1).equals(today))
//                .collect(Collectors.toList());
//    }
//
//    // Method to retrieve books needing an overdue alert
//    public List<BorrowedBooks> getBooksForOverdueAlert() {
//        LocalDate today = LocalDate.now();
//        return borrowedBooksList.stream()
//                .filter(book -> !book.getReturnDate().isEmpty())
//                .filter(book -> {
//                    LocalDate returnDate = LocalDate.parse(book.getReturnDate());
//                    return !returnDate.isAfter(today) && returnDate.plusDays(7).isAfter(today);
//                })
//                .collect(Collectors.toList());
//    }
//
//    public List<String> getEmailIdsByUserIds(List<String> userIds) {
//        SearchUsers search = new SearchUsers();
//        return search.getContactInfoByUserIds(userIds);
//    }
//    // Inside getUserIdsb method
//    public void getUserIdsb(List<BorrowedBooks> borrowedBooksList) {
//        List<String> userIds = new ArrayList<>();
//        for (BorrowedBooks book : borrowedBooksList) {
//            userIds.add(book.getUserId());
//        }
//        System.out.println("User IDs collected: " + userIds);
//        userEmails = getEmailIdsByUserIds(userIds);
//        System.out.println("User emails resolved: " + userEmails);
//    }
//
//    // Inside getBookNamesByBookIds method
//    public void getBookNamesByBookIds(List<BorrowedBooks> borrowedBooksList) {
//        List<String> bookIds = new ArrayList<>();
//        for (BorrowedBooks book : borrowedBooksList) {
//            bookIds.add(book.getBookId());
//        }
//        System.out.println("Book IDs collected: " + bookIds);
//        SearchBooks search = new SearchBooks();
//        bookNames = search.searchBookTitleByBookIds(bookIds);
//        System.out.println("Book names resolved: " + bookNames);
//    }
//
//    // Send alerts based on the list of BorrowedBooks objects
//    public void sendAlert(List<BorrowedBooks> books, String alertType) {
//        int i = 0;
//
//        // Check if the sizes of the lists are consistent
//        if (books.size() > bookNames.size() || books.size() > userEmails.size()) {
//            System.err.println("Mismatch in list sizes: books, bookNames, or userEmails may not be populated correctly.");
//            return;
//        }
//
//        for (BorrowedBooks book : books) {
//            String userId = book.getUserId();
//            String bookName = bookNames.get(i);
//            String userEmail = userEmails.get(i);
//
//            switch (alertType) {
//                case "renewal":
//                    Mail.sendRenewalMail(userEmail, userId, bookName, 3-book.getRenewalCount(), book.getRenewalDate());
//                    break;
//                case "return":
//                    Mail.sendReturnMail(userEmail, userId, bookName);
//                    break;
//                case "Overdue":
//                    Mail.sendOverdueMail(userEmail, userId, bookName);
//                    break;
//                default:
//                    System.out.println("Invalid alert type");
//            }
//            i++;
//        }
//    }
//}
package Mail;

import SearchQuerys.*;
import UserActions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class UserNotificationService {
    private List<String> userEmails;
    private List<String> bookNames;
    private List<BorrowedBooks> borrowedBooksList;

    public UserNotificationService(List<BorrowedBooks> borrowedBooksList) {
        this.borrowedBooksList = borrowedBooksList;
    }

    // Method to retrieve books needing a renewal alert
    public List<BorrowedBooks> getBooksForRenewalAlert() {
        LocalDate today = LocalDate.now();
        return borrowedBooksList.stream()
                .filter(book -> !book.getRenewalDate().isEmpty())
                .filter(book -> LocalDate.parse(book.getRenewalDate()).minusDays(1).equals(today))
                .collect(Collectors.toList());
    }

    // Method to retrieve books needing a return alert
    public List<BorrowedBooks> getBooksForReturnAlert() {
        LocalDate today = LocalDate.now();
        return borrowedBooksList.stream()
                .filter(book -> !book.getReturnDate().isEmpty())
                .filter(book -> LocalDate.parse(book.getReturnDate()).minusDays(1).equals(today))
                .collect(Collectors.toList());
    }

    // Method to retrieve books needing an overdue alert
    public List<BorrowedBooks> getBooksForOverdueAlert() {
        LocalDate today = LocalDate.now();
        return borrowedBooksList.stream()
                .filter(book -> !book.getReturnDate().isEmpty())
                .filter(book -> {
                    LocalDate returnDate = LocalDate.parse(book.getReturnDate());
                    return !returnDate.isAfter(today) && returnDate.plusDays(7).isAfter(today);
                })
                .collect(Collectors.toList());
    }

    public List<String> getEmailIdsByUserIds(List<String> userIds) {
        SearchUsers search = new SearchUsers();
        return search.getContactInfoByUserIds(userIds);
    }

    public void getUserIds(List<BorrowedBooks> borrowedBooksList) {
        List<String> userIds = new ArrayList<>();
        for (BorrowedBooks book : borrowedBooksList) {
            userIds.add(book.getUserId());
        }
        System.out.println("User IDs collected: " + userIds);
        userEmails = getEmailIdsByUserIds(userIds);
        System.out.println("User emails resolved: " + userEmails);
    }

    public void getBookNamesByBookIds(List<BorrowedBooks> borrowedBooksList) {
        List<String> bookIds = new ArrayList<>();
        for (BorrowedBooks book : borrowedBooksList) {
            bookIds.add(book.getBookId());
        }
        System.out.println("Book IDs collected: " + bookIds);
        SearchBooks search = new SearchBooks();
        bookNames = search.searchBookTitleByBookIds(bookIds);
        System.out.println("Book names resolved: " + bookNames);
    }

    // Send alerts based on the list of BorrowedBooks objects
    public void sendAlert(List<BorrowedBooks> books, String alertType) {
        int i = 0;

        if (books.size() > bookNames.size() || books.size() > userEmails.size()) {
            System.err.println("Mismatch in list sizes: books, bookNames, or userEmails may not be populated correctly.");
            return;
        }

        for (BorrowedBooks book : books) {
            String userId = book.getUserId();
            String bookName = bookNames.get(i);
            String userEmail = userEmails.get(i);

            switch (alertType) {
                case "renewal":
                    Mail.sendRenewalMail(userEmail, userId, bookName, 3 - book.getRenewalCount(), book.getRenewalDate());
                    break;
                case "return":
                    Mail.sendReturnMail(userEmail, userId, bookName);
                    break;
                case "Overdue":
                    Mail.sendOverdueMail(userEmail, userId, bookName);
                    break;
                default:
                    System.out.println("Invalid alert type");
            }
            i++;
        }
    }
    public int sendBlockedUserAlert() {
        List<BorrowedBooks> blockedUsers = borrowedBooksList.stream()
                .filter(book -> book.getRenewalCount() == -1)
                .collect(Collectors.toList());

        getUserIds(blockedUsers);
        getBookNamesByBookIds(blockedUsers);

        // Check for missing data before proceeding
        if (userEmails.size() < blockedUsers.size() || bookNames.size() < blockedUsers.size()) {
            System.out.println("Error: Missing email addresses or book names for some users.");

            // Log missing data errors
            for (int i = 0; i < blockedUsers.size(); i++) {
                if (i >= userEmails.size()) {
                    System.out.println("Missing email for user: " + blockedUsers.get(i).getUserId());
                }
                if (i >= bookNames.size()) {
                    System.out.println("Missing book name for book ID: " + blockedUsers.get(i).getBookId());
                }
            }
            System.out.println("Aborting alert process due to incomplete data.");
            return 0;  // Exit the function immediately if data is incomplete
        }

        // If data is complete, proceed to send the alert
        int i = 0;
        for (BorrowedBooks book : blockedUsers) {
            String userEmail = userEmails.get(i);
            String bookName = bookNames.get(i);
            String userId = book.getUserId();

            try {
                Mail.sendBlockedUserMail(userEmail, userId, bookName);
                System.out.println("Mail sent successfully to user: " + userId);
            } catch (Exception e) {
                System.out.println("Failed to send mail to user: " + userId + " (" + userEmail + "). Error: " + e.getMessage());
            }
            i++;
        }

        // This message should only print after all alerts are successfully sent
        System.out.println("Blocked user alert processing completed.");
        return 1;
    }





}
