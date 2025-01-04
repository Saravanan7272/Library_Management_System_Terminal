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
public class OTPVerification {
    private OTPManager otpManager = new OTPManager();
    private String recipientEmail;

    public boolean initiateOTPGeneration(String recipientEmail) {
        this.recipientEmail = recipientEmail;
        // Generate OTP and save it to the file
        String generatedOtp = otpManager.generateOTP();
        // Attempt to send the OTP via email
        boolean emailSent = sendOTPViaEmail(generatedOtp);  // No exception, returns true/false
        // Check if the email was successfully sent
        if (!emailSent) {
            System.out.println("Failed to send OTP. Please check the email address and try again.");
            return false;  // Email sending failed
        }
        return true;  // OTP generation and email sending succeeded
    }



    // Method to verify the OTP entered by the user
    public boolean verifyEnteredOTP(String enteredOTP) {
        return otpManager.verifyOTP(enteredOTP);
    }

    private boolean sendOTPViaEmail(String otp) {
        System.out.println("Sending OTP to email: " + recipientEmail);
        // Call the email sending function and check if it succeeds
        boolean emailSent = Mail.sendOTPVerificationMail(recipientEmail, otp);

        if (emailSent) {
           // System.out.println("OTP successfully sent to email.");
            return true;  // Email was sent successfully
        } else {
            System.out.println("Failed to send OTP to email. Please check the email address.");
            return false;  // Email sending failed
        }
    }


}
