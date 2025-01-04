package Login;

// LibraryMember class extending User
public class LibraryMember extends User implements Login {
    public LibraryMember() {
        super("", "", ""); // Call to the superclass constructor with empty values
    }

    public LibraryMember(String userId, String username, String password) {
        super(userId, username, password);
    }

    @Override
    public boolean login(String userId, String password) {
        // Member-specific login logic based on userId
        return this.getUserId().equals(userId) && this.getPassword().equals(password);
    }

    @Override
    public boolean logout() {
        // Member-specific logout logic
        return true;
    }
}