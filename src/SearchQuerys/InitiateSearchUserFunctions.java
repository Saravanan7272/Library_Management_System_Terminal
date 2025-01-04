package SearchQuerys;
import FileFunction.CSVReader;
import Login.User;
import Users.Faculty;
import Users.Student;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class InitiateSearchUserFunctions {

    public static void start(Scanner scanner) {
        SearchUsers searchUsers = new SearchUsers(); // Initialize SearchUsers to manage searches

        while (true) {
            System.out.println("\nSelect a search Option:");

            // Student related search options first
            System.out.println("1. Search Student by Username");
            System.out.println("2. Search Student by User ID");
            System.out.println("3. Search Student by Batch");
            System.out.println("4. Search Student by Year");
            System.out.println("5. Search Student by Contact Info");
            System.out.println("6. Search by Multiple Criteria (Student)");

            // Faculty related search options follow
            System.out.println("7. Search Faculty by Username");
            System.out.println("8. Search Faculty by User ID");
            System.out.println("9. Search Faculty by Department");
            System.out.println("10. Search Faculty by Contact Info");
            System.out.println("11. Search by Multiple Criteria (Faculty)");

            // Exit option
            System.out.println("12. Exit");

            System.out.print("Your choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("12")) {
                break;
            }

            switch (choice) {
                // Student related searches
                case "1":
                    System.out.print("Enter student username: ");
                    String username = scanner.nextLine();
                    List<Student> studentsByUsername = searchUsers.searchStudents(username, null, null, null, null);
                    displayResults(studentsByUsername);
                    break;

                case "2":
                    System.out.print("Enter student user ID: ");
                    String userId = scanner.nextLine();
                    List<Student> studentsByUserId = searchUsers.searchStudents(null, userId, null, null, null);
                    displayResults(studentsByUserId);
                    break;

                case "3":
                    System.out.print("Enter student batch: ");
                    String batch = scanner.nextLine();
                    List<Student> studentsByBatch = searchUsers.searchStudents(null, null, batch, null, null);
                    displayResults(studentsByBatch);
                    break;

                case "4":
                    System.out.print("Enter student year: ");
                    Integer year = Integer.parseInt(scanner.nextLine());
                    List<Student> studentsByYear = searchUsers.searchStudents(null, null, null, year, null);
                    displayResults(studentsByYear);
                    break;

                case "5":
                    System.out.print("Enter student contact info (email/phone): ");
                    String contactInfo = scanner.nextLine();
                    List<Student> studentsByContactInfo = searchUsers.searchStudents(null, null, null, null, contactInfo);
                    displayResults(studentsByContactInfo);
                    break;

                case "6":
                    System.out.print("Enter student username (or leave empty): ");
                    String multiUsername = scanner.nextLine();
                    System.out.print("Enter student user ID (or leave empty): ");
                    String multiUserId = scanner.nextLine();
                    System.out.print("Enter student batch (or leave empty): ");
                    String multiBatch = scanner.nextLine();
                    System.out.print("Enter student year (or leave empty): ");
                    Integer multiYear = scanner.hasNextInt() ? Integer.parseInt(scanner.nextLine()) : null;
                    System.out.print("Enter student contact info (or leave empty): ");
                    String multiContactInfo = scanner.nextLine();

                    List<Student> studentsByMultipleCriteria = searchUsers.searchStudents(
                            multiUsername.isEmpty() ? null : multiUsername,
                            multiUserId.isEmpty() ? null : multiUserId,
                            multiBatch.isEmpty() ? null : multiBatch,
                            multiYear,
                            multiContactInfo.isEmpty() ? null : multiContactInfo
                    );
                    displayResults(studentsByMultipleCriteria);
                    break;

                // Faculty related searches
                case "7":
                    System.out.print("Enter faculty username: ");
                    String facultyUsername = scanner.nextLine();
                    List<Faculty> facultyByUsername = searchUsers.searchFaculty(facultyUsername, null, null, null);
                    displayResults(facultyByUsername);
                    break;

                case "8":
                    System.out.print("Enter faculty user ID: ");
                    String facultyUserId = scanner.nextLine();
                    List<Faculty> facultyByUserId = searchUsers.searchFaculty(null, facultyUserId, null, null);
                    displayResults(facultyByUserId);
                    break;

                case "9":
                    System.out.print("Enter faculty department: ");
                    String department = scanner.nextLine();
                    List<Faculty> facultyByDepartment = searchUsers.searchFaculty(null, null, department, null);
                    displayResults(facultyByDepartment);
                    break;

                case "10":
                    System.out.print("Enter faculty contact info (email/phone): ");
                    String facultyContactInfo = scanner.nextLine();
                    List<Faculty> facultyByContactInfo = searchUsers.searchFaculty(null, null, null, facultyContactInfo);
                    displayResults(facultyByContactInfo);
                    break;

                case "11":
                    System.out.print("Enter faculty username (or leave empty): ");
                    String multiFacultyUsername = scanner.nextLine();
                    System.out.print("Enter faculty user ID (or leave empty): ");
                    String multiFacultyUserId = scanner.nextLine();
                    System.out.print("Enter faculty department (or leave empty): ");
                    String multiDepartment = scanner.nextLine();
                    System.out.print("Enter faculty contact info (or leave empty): ");
                    String multiFacultyContactInfo = scanner.nextLine();

                    List<Faculty> facultyByMultipleCriteria = searchUsers.searchFaculty(
                            multiFacultyUsername.isEmpty() ? null : multiFacultyUsername,
                            multiFacultyUserId.isEmpty() ? null : multiFacultyUserId,
                            multiDepartment.isEmpty() ? null : multiDepartment,
                            multiFacultyContactInfo.isEmpty() ? null : multiFacultyContactInfo
                    );
                    displayResults(facultyByMultipleCriteria);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // Helper function to display search results
    private static void displayResults(List<?> users) {
        if (users.isEmpty()) {
            System.out.println("No results found.");
        } else {
            // Print a header for the results based on user type
            System.out.println("Search Results:");
            System.out.println("-".repeat(100));

            // Check if the first element is a Student or Faculty and display appropriate headers
            if (users.get(0) instanceof Student) {
                System.out.println("User ID | Username | Batch | Year | Contact Info");
            } else if (users.get(0) instanceof Faculty) {
                System.out.println("User ID | Username | Department | Contact Info");
            }

            System.out.println("-".repeat(100)); // Line separator

            // Print user details
            for (Object user : users) {
                System.out.println(user);
                System.out.println("-".repeat(100));  // Separator after each user's details
            }
        }
    }


}
