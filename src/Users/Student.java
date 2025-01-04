package Users;
import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import Users.*;
public class Student extends LibraryUser {
    private String batch;
    private int year;
    public Student() {

    }

    // Constructor
    public Student(String userId, String username, String password, String type, String contactInfo, String batch, int year) {
        super(userId, username, password, type, contactInfo);
        this.batch = batch;
        this.year = year;
    }

    // Getters and Setters
    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\u001B[32mBatch: \u001B[0m" + batch + "\n" +
                "\u001B[32mYear: \u001B[0m" + year + "\n";
    }


}
