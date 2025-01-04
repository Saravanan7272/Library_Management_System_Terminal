package DateUtils;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class DateUtils {
    // Static constants for renewal and return periods
    public static final int RENEWAL_PERIOD_DAYS = 7;
    public static final int RETURN_PERIOD_DAYS = 28;
    public static final int PICKUP_DEADLINE_DAYS = 3; // New constant for pickup deadline

    // Method to get the current date in the "YYYY-MM-DD" format
    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        return formatDate(currentDate);
    }

    // Method to calculate the renewal date (7 days after the checkout date)
    public static String getRenewalDate() {
        LocalDate renewalDate = LocalDate.now().plusDays(RENEWAL_PERIOD_DAYS);
        return formatDate(renewalDate);
    }

    // Method to calculate the return date (28 days after the checkout date)
    public static String getReturnDate() {
        LocalDate returnDate = LocalDate.now().plusDays(RETURN_PERIOD_DAYS);
        return formatDate(returnDate);
    }

    // New method to calculate the pickup deadline (3 days after the reservation date)
    public static String getPickupDeadline() {
        LocalDate pickupDeadline = LocalDate.now().plusDays(PICKUP_DEADLINE_DAYS);
        return formatDate(pickupDeadline);
    }

    // Method to format LocalDate to the "YYYY-MM-DD" format
    private static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}

