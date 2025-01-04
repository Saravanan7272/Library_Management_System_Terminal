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
// Utility class for authentication logic
public class SecurityTokenCheck
{
    private static final String ADMIN_SECURITY_TOKEN = "secureAdmin123";
 //  private static final String ADMIN_SECURITY_TOKEN = "1";
    public static boolean handleAdminSecurityToken(int for_login) {
        if(for_login==0) {
            System.out.println("\u001B[31m" + "╔════════════════════════════════════════════════════════════════════════════════════════════════╗" + "\u001B[0m");
            System.out.println("\u001B[31m" + "║" + "\u001B[34m" + "Security Notice: Admin access is required to modify library data. Please enter the security key." + "\u001B[0m" + "\u001B[31m" + "║" + "\u001B[0m");
            System.out.println("\u001B[31m" + "╚════════════════════════════════════════════════════════════════════════════════════════════════╝" + "\u001B[0m");
        }
        if(for_login==1) {
            System.out.println("\u001B[31m" + "╔═══════════════════════════════════════════════════════════════╗" + "\u001B[0m");
            System.out.println("\u001B[31m" + "║" + "\u001B[34m" + "   For additional security, you must enter the security key.   " + "\u001B[0m" + "\u001B[31m" + "║" + "\u001B[0m");
            System.out.println("\u001B[31m" + "╚═══════════════════════════════════════════════════════════════╝" + "\u001B[0m");
        }
        System.out.print("Enter security token for admin access: ");
        Scanner scanner = new Scanner(System.in);
        String enteredToken = scanner.nextLine();

        if (enteredToken.equals(ADMIN_SECURITY_TOKEN)) {
            System.out.println("\u001B[32m"+"Admin access granted."+"\u001B[0m");
            return true;
        } else {
            System.out.println("\u001B[31m"+"Invalid security token. Access denied."+"\u001B[0m");
            return false;
        }
    }

}
