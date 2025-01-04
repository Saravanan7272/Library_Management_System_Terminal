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
// Admin class extending User
public class Admin extends User implements Login {
    public Admin() {
        super("", "", ""); // Call to the superclass constructor with empty values
    }

    public Admin(String userId, String username, String password) {
        super(userId, username, password);
    }

    @Override
    public boolean login(String userId, String password) {
        // Admin-specific login logic based on userId
        return this.getUserId().equals(userId) && this.getPassword().equals(password);
    }

    @Override
    public boolean logout() {
        // Admin-specific logout logic
        return true;
    }
}