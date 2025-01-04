package UserActions;

import FileFunction.CSVReader;
import FileFunction.CSVWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LibraryDataCleaner {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // Method to delete expired reservations
    public static void deleteExpiredReservations(List<Reservation> reservationList, String filePath) {
        LocalDate currentDate = LocalDate.now();

        // Remove reservations where the current date is after the pickup deadline
        reservationList.removeIf(reservation -> {
            try {
                LocalDate pickupDeadline = LocalDate.parse(reservation.getPickupDeadline(), formatter);
                return currentDate.isAfter(pickupDeadline);
            } catch (Exception e) {
                System.err.println("Error processing reservation for BookID: " + reservation.getBookId());
                e.printStackTrace();
                return false;  // Don't remove in case of error
            }
        });


        try {
            CSVWriter.writeObjectsToCSV(filePath, reservationList, 1);
        } catch (Exception e) {
            System.err.println("Error writing updated reservations to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void deleteUserOldCartCollections(List<CartCollection> cartCollectionList, String filePath, int daysToLimit) {
        LocalDate currentDate = LocalDate.now();

        // Remove cart collections where dateAdded + daysToLimit is before the current date
        cartCollectionList.removeIf(cart -> {
            try {
                LocalDate dateAdded = LocalDate.parse(cart.getDateAdded(), formatter);
                return currentDate.isAfter(dateAdded.plusDays(daysToLimit));
            } catch (Exception e) {
                System.err.println("Error processing cart item with BookID: " + cart.getBookId());
                e.printStackTrace();
                return false;  // Don't remove in case of error
            }
        });
        try {
            CSVWriter.writeObjectsToCSV(filePath, cartCollectionList, 1);
        } catch (Exception e) {
            System.err.println("Error writing updated cart collections to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

        public static void main(String[] args) {
            try {
                // Read the reservations from the file
                List<Reservation> reservationList = CSVReader.readCSV("Databases/Reservations.csv", Reservation.class);
                LibraryDataCleaner.deleteExpiredReservations(reservationList, "Databases/Reservations.csv");
                System.out.println("Expired reservations deleted successfully.");
            } catch (Exception e) {
                System.err.println("Error deleting expired reservations: " + e.getMessage());
                e.printStackTrace();
            }
            try {
                List<CartCollection> cartCollectionList = CSVReader.readCSV("Databases/CartCollections.csv", CartCollection.class);

                LibraryDataCleaner.deleteUserOldCartCollections(cartCollectionList, "Databases/CartCollections.csv", 30);
                System.out.println("Old cart collections deleted successfully.");
            } catch (Exception e) {
                System.err.println("Error deleting old cart collections: " + e.getMessage());
                e.printStackTrace();
            }
        }


}
