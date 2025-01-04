package Mail;
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

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {

    private static Session newSession;

    static {
        setupServerProperties();
    }

    private static void setupServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(properties, null);
    }

    private static boolean sendEmail(String recipientEmail, String subject, String body) {
        String fromUser = "eesakkisaravanan997@gmail.com";  // Enter sender email id
        String fromUserPassword = "gejh uzui lsnz olfo";   // Enter sender email password

        MimeMessage mimeMessage;
        Transport transport = null;

        try {
            mimeMessage = new MimeMessage(newSession);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            mimeMessage.setSubject(subject);

            // Create the email body
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(body, "text/html");

            MimeMultipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(bodyPart);
            mimeMessage.setContent(multiPart);

            // Send the email
            transport = newSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", fromUser, fromUserPassword);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            System.out.println("Email successfully sent to " + recipientEmail + "!!!");
            return true;  // Email sent successfully
        } catch (MessagingException e) {
            // Handle specific cases
            if (e.getMessage().contains("550")) {
                System.out.println("Error: The email address does not exist.");
            } else if (e.getMessage().contains("timed out")) {
                System.out.println("Error: Connection timed out. Please check your internet connection.");
            } else {
                System.out.println("Error sending email: " + e.getMessage());
            }
            e.printStackTrace();
            return false;  // Email failed to send
        } finally {
            if (transport != null && transport.isConnected()) {
                try {
                    transport.close();  // Close transport in the finally block
                } catch (MessagingException e) {
                    System.out.println("Error closing transport: " + e.getMessage());
                }
            }
        }
    }
    public static void sendRenewalMail(String recipientEmail, String userId, String bookName, int renewalCount, String renewalDueDate) {
        String subject = "Library Membership Renewal Notification";
        String body = String.format(
                "Dear User (ID: <b>%s</b>),<br><br>" +
                        "This is a reminder that your library membership renewal is approaching. Below are the details:<br><br>" +
                        "Book Name: <b>%s</b><br>" +
                        "Renewals Left: <b>%d</b><br>" +
                        "Renewal Due Date: <b>%s</b><br><br>" +
                        "Please ensure to complete your renewal by the due date to avoid any additional charges to your account.<br><br>" +
                        "Thank you for your attention to this matter.<br><br>" +
                        "<b>Note</b>: If you received this email in error or wrongly, please ignore it.",
                userId, bookName, renewalCount, renewalDueDate
        );
        sendEmail(recipientEmail, subject, body);
    }

    public static void sendReturnMail(String recipientEmail, String userId, String bookName) {
        String subject = "Library Book Return Required";
        String body = String.format(
                "Dear User (ID: <b>%s</b>),<br><br>" +
                        "This is a reminder that the book titled \"<b>%s</b>\" must be returned to the library, as the renewal period has ended.<br><br>" +
                        "Please ensure to return the book promptly to avoid any late fees or penalties.<br><br>" +
                        "Thank you for your cooperation.<br><br>" +
                        "<b>Note</b>: If you received this email in error or wrongly, please ignore it.",
                userId, bookName
        );
        sendEmail(recipientEmail, subject, body);
    }

    public static boolean sendOTPVerificationMail(String recipientEmail, String otp) {
        String subject = "OTP Verification for Library Services";
        String body = String.format(
                "Dear User,<br><br>" +
                        "Your One-Time Password (OTP) for verification is: <b>%s</b><br><br>" +
                        "Please use this OTP to complete your action within the stipulated time.<br><br>" +
                        "Thank you for using our library!<br><br>" +
                        "<b>Note</b>: If you received this email in error or wrongly, please ignore it.",
                otp
        );

        boolean isSent = sendEmail(recipientEmail, subject, body);
        if (!isSent) {
            System.out.println("Failed to send email to " + recipientEmail + ". Please check the email address.");
        }
        return isSent;
    }

    public static void sendOverdueMail(String recipientEmail, String userId, String bookName) {
        String subject = "Overdue Book Reminder Notification";
        String body = String.format(
                "Dear User (ID: <b>%s</b>),<br><br>" +
                        "This is a final reminder that the book titled \"<b>%s</b>\" is currently overdue.<br><br>" +
                        "Please return it at your earliest convenience to avoid any additional penalties. Continued delays may result in the blocking of your library account.<br><br>" +
                        "Thank you for your attention to this matter.<br><br>" +
                        "<b>Note</b>: If you received this email in error or wrongly, please ignore it.",
                userId, bookName
        );
        sendEmail(recipientEmail, subject, body);
    }
    public static void sendBlockedUserMail(String recipientEmail, String userId, String bookName) {
        String subject = "Blocked Account Due to Overdue Book";
        String body = String.format(
                "Dear User (ID: <b>%s</b>),<br><br>" +
                        "Our records indicate that the book titled \"<b>%s</b>\" is overdue, and as a result, your library account has been <b>blocked</b>.<br><br>" +
                        "Please return the book immediately to restore your account access. Continued failure to return overdue items may incur additional penalties.<br><br>" +
                        "We apologize for any inconvenience this may cause and appreciate your prompt attention to this matter.<br><br>" +
                        "<b>Note</b>: If you received this email in error or wrongly, please ignore it.",
                userId, bookName
        );
        sendEmail(recipientEmail, subject, body);
    }
}
