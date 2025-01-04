package Login;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import java.io.*;
import java.util.*;
import Users.*;
import java.util.Scanner;
public class OTPHandler {
    private OTPVerification otpVerification = new OTPVerification();

    public boolean initiateOTP(String contactInfo) {
        System.out.println("Generating OTP and sending to your email...");
        return otpVerification.initiateOTPGeneration(contactInfo);
    }

    public String verifyOTP(Scanner scanner) {
        //scanner.nextLine();
        String enteredOtp;
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter the OTP sent to your email: ");
            enteredOtp = scanner.nextLine();
            if (otpVerification.verifyEnteredOTP(enteredOtp)) {
                System.out.println("OTP verified successfully! Proceeding with sign-up.");
                return enteredOtp;
            } else {
                attempts--;
                System.out.println("Incorrect OTP! You have " + attempts + " attempt(s) left.");
            }
        }
        System.out.println("OTP verification failed! Please restart the sign-up process.");
        return null;
    }
}
