package Login;
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
import javax.mail.MessagingException;
import java.io.*;
import java.util.Random;
public class OTPManager {
    private static final String OTP_FILE = "Databases/Otp.txt"; // Path to save the OTP file
    private String otp;

    // Method to generate a 6-digit OTP
    public String generateOTP() {
        Random random = new Random();
        int generatedOtp = 100000 + random.nextInt(900000); // Generate a random 6-digit OTP
        this.otp = String.valueOf(generatedOtp);
       // System.out.println("Generated OTP: " + otp);
        saveOTPToFile(this.otp); // Save the OTP to the file
        return this.otp;
    }

    // Save OTP to a file, creating it if it doesn't exist
    private void saveOTPToFile(String otp) {
        try {
            // Create the file if it does not exist
            File file = new File(OTP_FILE);
            if (!file.exists()) {
                file.createNewFile(); // Create a new file if it doesn't exist
                System.out.println("File created: " + OTP_FILE);
            }

            // Write the OTP to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(otp);
                writer.flush(); // Ensure data is written to the file
                //System.out.println("OTP successfully written to the file: " + OTP_FILE);
            }
        } catch (IOException e) {
            System.out.println("Error saving OTP to file: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    // Read OTP from the file
    public String readOTPFromFile() {
        String otp = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(OTP_FILE))) {
            otp = reader.readLine(); // Read the first line (the OTP)
            //System.out.println("Read OTP from file: " + otp);
        } catch (IOException e) {
           System.out.println("Error reading OTP from file: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
        return otp;
    }

    // Verify the OTP entered by the user
    public boolean verifyOTP(String enteredOTP) {
        String savedOTP = readOTPFromFile(); // Get the saved OTP from the file
        boolean isValid = enteredOTP != null && enteredOTP.equalsIgnoreCase(savedOTP);

        // If the OTP is valid, clear the file content
        if (isValid) {
            clearOTPFile();
        }
        return isValid; // Return whether the OTP is valid
    }

    // Clear the content of the OTP file after successful verification
    private void clearOTPFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OTP_FILE))) {
            writer.write(""); // Overwrite the file with an empty string
            writer.flush(); // Ensure that the file content is cleared immediately
           // System.out.println("OTP file content successfully cleared.");
        } catch (IOException e) {
            System.out.println("Error clearing OTP file content: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
}
