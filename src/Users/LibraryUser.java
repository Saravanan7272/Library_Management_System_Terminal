package Users;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
// Base Class: User
public class LibraryUser {
    private String userId;
    private String username;
    private String password;
    private String type;
    private String contactInfo;

    // Default Constructor
    public LibraryUser() {}

    // Constructor with parameters
    public LibraryUser(String userId, String username, String password, String type, String contactInfo) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.type = type;
        this.contactInfo = contactInfo;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "\n\u001B[1;34mUser Details:\u001B[0m\n" +
                "\u001B[36mUser ID: \u001B[0m" + userId + "\n" +
                "\u001B[35mUsername: \u001B[0m" + username + "\n" +
                "\u001B[33mPassword: \u001B[0m" + password + "\n" +
                "\u001B[32mType: \u001B[0m" + type + "\n" +
                "\u001B[34mContact Info: \u001B[0m" + contactInfo + "\n";
    }
    public static String getHeader() {
        return "\u001B[1;34mUser Details Header:\u001B[0m\n" +
                "\u001B[36m%-15s %-20s %-15s %-10s %-20s\u001B[0m".formatted(
                        "User ID", "Username", "Password", "Type", "Contact Info");
    }



}



