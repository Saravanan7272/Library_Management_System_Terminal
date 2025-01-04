package Login;

// Login interface
public interface Login {
    boolean login(String userId, String password); // Changed parameter name from username to userId
    boolean logout();
}
