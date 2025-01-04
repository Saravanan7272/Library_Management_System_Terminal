import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
import Main.*;
import com.sun.net.httpserver.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean isSuccess = IniciateLoginClass.start();

        if (isSuccess) {
            //System.out.println("Login successful! You can now access the system features.");
            String userType = IniciateLoginClass.currentMember != null
                    ? IniciateLoginClass.currentMember.getClass().getSimpleName()
                    : IniciateLoginClass.currentUser.getClass().getSimpleName();
            switch (userType) {
                case "Student":
                    //System.out.println("Logged in as: Student");
                    UserMain.studentAction(scanner,IniciateLoginClass.currentMember);
                    break;

                case "Faculty":
                   // System.out.println("Logged in as: Faculty");
                    UserMain.facultyAction(scanner,IniciateLoginClass.currentMember);
                    break;
                case "Admin":
                    //System.out.println("Logged in as: Admin");
                    AdminMain.start(IniciateLoginClass.currentUser);
                    break;

                default:
                    System.out.println("Unknown user role. Exiting the system.");
            }
        } else {
            System.out.println("Login failed or the user chose to exit. Exiting the application.");
        }
        scanner.close();
    }
}
