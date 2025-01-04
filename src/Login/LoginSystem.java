package Login;

import Books.*;
import DateUtils.*;
import FileFunction.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import java.io.*;
import java.util.*;

public class LoginSystem {
    // Perform login and add extra authentication for admin
    public boolean performLogin(Login user, String userId, String password) {
        if (user.login(userId, password)) {
            if (user instanceof Admin) {
                return SecurityTokenCheck.handleAdminSecurityToken(1);  // Reusing authentication method
            }
            return true;  // Normal user login
        }
        return false;  // Login failed
    }

    // Perform logout
    public boolean performLogout(Login user) {
        return user.logout();
    }
}
