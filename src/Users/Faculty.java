package Users;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
// Subclass: Faculty
public class Faculty extends LibraryUser {
    private String department;
    public  Faculty(){

    }
    // Constructor
    public Faculty(String userId, String username, String password, String type, String contactInfo, String department) {
        super(userId, username, password, type, contactInfo);
        this.department = department;
    }

    // Getters and Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\u001B[32mDepartment: \u001B[0m" + department + "\n";
    }

}
