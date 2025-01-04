//package Users;
//
//import Books.*;
//import DateUtils.*;
//import FileFunction.*;
//import InputValidationUtils.ValidationUtils;
//import Login.*;
//import Mail.*;
//import SearchQuerys.*;
//import UserActions.*;
//import java.io.*;
//import java.util.*;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//import java.util.Scanner;
//import java.util.function.Consumer;
//import java.util.function.IntConsumer;
//import java.util.Scanner;
//
//import static java.lang.System.exit;
//
//public class LibraryUserManager {
//    private final ManageUsersDetails userManager = new ManageUsersDetails();
//
//
//    // Method to add a new user
//    public void addNewUser(Scanner scanner) {
//        System.out.println("Adding New User... Please enter the details.");
//        try {
//            SignUp signUp = new SignUp();
//            if (signUp.addNewMember(scanner)) {
//                System.out.println("New user added successfully.");
//            } else {
//                System.out.println("User addition was unsuccessful.");
//            }
//        } catch (Exception e) {
//            System.out.println("Error adding new user: " + e.getMessage());
//        }
//    }
//
//    // Method to update user details
//    public void updateUser(Scanner scanner) {
//        String userIdToUpdate = ValidationUtils.promptStringWithValidation("Enter the User ID of the user to update", 3, ValidationUtils::isValidUserId,ValidationUtils.getUserIdErrorMessage());
//        if (userIdToUpdate == null) exit(0);
//        try {
//            LibraryUser userToUpdate = userManager.searchUserById(userIdToUpdate);
//            if (userToUpdate != null) {
//                System.out.println("Current user details: " + userToUpdate);
//                boolean continueUpdating = true;
//                boolean isUpdated = false;
//                while (continueUpdating) {
//                    updateUserFields(userToUpdate, scanner);
//                    isUpdated = true;
//                    String choice = promptString("Do you want to update another field For this User? (yes-1/no-0)", scanner).trim().toLowerCase();
//                    if (choice.equals("0")) {
//                        continueUpdating = false;
//                        System.out.println("No more fields will be updated.");
//                    } else if (choice.equals("1")) {
//                        continueUpdating = true;
//                    } else {
//                        continueUpdating = false;
//                        System.out.println("No more fields will be updated.");
//                    }
//                }
//                if (isUpdated) {
//                    userManager.updateUserDetails(userToUpdate);
//                    System.out.println("User details updated successfully.");
//                } else {
//                    System.out.println("No updates were made to the user details.");
//                }
//            } else {
//                System.out.println("User with ID " + userIdToUpdate + " not found.");
//            }
//        } catch (Exception e) {
//            System.out.println("Error updating user: " + e.getMessage());
//        }
//    }
//
//
//    // Method to remove a user
//    public void removeUser(Scanner scanner) {
//        String userId = ValidationUtils.promptStringWithValidation("Enter the User ID of the user to remove", 3, ValidationUtils::isValidUserId,ValidationUtils.getUserIdErrorMessage());
//        if (userId == null) exit(0);
//
//        try {
//            LibraryUser userToRemove = userManager.searchUserById(userId);
//            if (userToRemove != null) {
//                if (userToRemove instanceof Student) {
//                    userManager.removeStudent((Student) userToRemove);
//                } else if (userToRemove instanceof Faculty) {
//                    userManager.removeFaculty((Faculty) userToRemove);
//                }
//                System.out.println("User removed successfully.");
//            } else {
//                System.out.println("User with ID " + userId + " not found.");
//            }
//        } catch (Exception e) {
//            System.out.println("Error removing user: " + e.getMessage());
//        }
//    }
//
//    // Helper method for user field updates
//    private void updateUserFields(LibraryUser userToUpdate,Scanner scanner) {
//        System.out.println("Select the field to update:");
//        System.out.println("1. Username\n2. Password\n3. Contact Info");
//
//        if (userToUpdate instanceof Student) {
//            System.out.println("4. Batch\n5. Year");
//        } else if (userToUpdate instanceof Faculty) {
//            System.out.println("4. Department");
//        }
//        System.out.print("Enter The Choice:");
//        String choice = scanner.nextLine().trim();
//        try {
//            switch (choice) {
//                case "1":
//                    updateField("Username", userToUpdate::setUsername,scanner);
//                    break;
//                case "2":
//                    updateField("Password", userToUpdate::setPassword,scanner);
//                    break;
//                case "3":
//                    updateField("Contact Info", userToUpdate::setContactInfo,scanner);
//                    break;
//                case "4":
//                    if (userToUpdate instanceof Student) {
//                        updateField("Batch", ((Student) userToUpdate)::setBatch,scanner);
//                    } else if (userToUpdate instanceof Faculty) {
//                        updateField("Department", ((Faculty) userToUpdate)::setDepartment,scanner);
//                    }
//                    break;
//                case "5":
//                    if (userToUpdate instanceof Student) {
//                        updateIntField("Year", ((Student) userToUpdate)::setYear,scanner);
//                    }
//                    break;
//                default:
//                    System.out.println("Invalid choice.");
//            }
//        } catch (Exception e) {
//            System.out.println("Error updating field: " + e.getMessage());
//        }
//    }
//
//    // Helper methods
//    private String promptString(String message,Scanner scanner) {
//        System.out.print(message + ": ");
//        return scanner.nextLine();
//    }
//
//    private void updateField(String fieldName, Consumer<String> setter,Scanner scanner) {
//        String newValue = promptString("Enter new " + fieldName,scanner);
//        setter.accept(newValue);
//    }
//
//    private void updateIntField(String fieldName, IntConsumer setter,Scanner scanner) {
//        try {
//            int newValue = Integer.parseInt(promptString("Enter new " + fieldName,scanner));
//            setter.accept(newValue);
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid input. Please enter a valid number.");
//        }
//    }
//}
package Users;

import Books.*;
import DateUtils.*;
import FileFunction.*;
import InputValidationUtils.ValidationUtils;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import static java.lang.System.exit;

public class LibraryUserManager {
    private final ManageUsersDetails userManager = new ManageUsersDetails();

    // Method to add a new user
    public void addNewUser(Scanner scanner) {
        System.out.println("Adding New User... Please enter the details.");
        try {
            SignUp signUp = new SignUp();
            if (signUp.addNewMember(scanner)) {
                System.out.println("New user added successfully.");
            } else {
                System.out.println("User addition was unsuccessful.");
            }
        } catch (Exception e) {
            System.out.println("Error adding new user: " + e.getMessage());
        }
    }

    // Method to update user details
    public void updateUser(Scanner scanner) {
        String userIdToUpdate = ValidationUtils.promptStringWithValidation(
                "Enter the User ID of the user to update",
                3,
                ValidationUtils::isValidUserId,
                ValidationUtils.getUserIdErrorMessage()
        );
        if (userIdToUpdate == null) exit(0);

        try {
            LibraryUser userToUpdate = userManager.searchUserById(userIdToUpdate);
            if (userToUpdate != null) {
                System.out.println("Current user details: " + userToUpdate);
                boolean isUpdated = false;

                do {
                    updateUserFields(userToUpdate, scanner);
                    isUpdated = true;

                    String choice = promptString("Do you want to update another field for this User? (yes-1/no-0)", scanner).trim().toLowerCase();
                    if (!choice.equals("1")) break;
                } while (true);

                if (isUpdated) {
                    userManager.updateUserDetails(userToUpdate);
                    System.out.println("User details updated successfully with new fields.");
                } else {
                    System.out.println("No updates were made to the user details.");
                }
            } else {
                System.out.println("User with ID " + userIdToUpdate + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    // Method to remove a user
    public void removeUser(Scanner scanner) {
        String userId = ValidationUtils.promptStringWithValidation(
                "Enter the User ID of the user to remove",
                3,
                ValidationUtils::isValidUserId,
                ValidationUtils.getUserIdErrorMessage()
        );
        if (userId == null) exit(0);

        try {
            LibraryUser userToRemove = userManager.searchUserById(userId);
            if (userToRemove != null) {
                if (userToRemove instanceof Student) {
                    userManager.removeStudent((Student) userToRemove);
                } else if (userToRemove instanceof Faculty) {
                    userManager.removeFaculty((Faculty) userToRemove);
                }
                System.out.println("User removed successfully.");
            } else {
                System.out.println("User with ID " + userId + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error removing user: " + e.getMessage());
        }
    }

    // Helper method for user field updates
    private void updateUserFields(LibraryUser userToUpdate, Scanner scanner) {
        System.out.println("Select the field to update:");
        System.out.println("1. Username\n2. Password\n3. Contact Info");

        if (userToUpdate instanceof Student) {
            System.out.println("4. Batch\n5. Year");
        } else if (userToUpdate instanceof Faculty) {
            System.out.println("4. Department");
        }

        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        try {
            switch (choice) {
                case "1":
                    updateField("Username", userToUpdate::setUsername, scanner);
                    break;
                case "2":
                    updateField("Password", userToUpdate::setPassword, scanner);
                    break;
                case "3":
                    updateField("Contact Info", userToUpdate::setContactInfo, scanner);
                    break;
                case "4":
                    if (userToUpdate instanceof Student) {
                        updateField("Batch", ((Student) userToUpdate)::setBatch, scanner);
                    } else if (userToUpdate instanceof Faculty) {
                        updateField("Department", ((Faculty) userToUpdate)::setDepartment, scanner);
                    }
                    break;
                case "5":
                    if (userToUpdate instanceof Student) {
                        updateIntField("Year", ((Student) userToUpdate)::setYear, scanner);
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Error updating field: " + e.getMessage());
        }
    }

    // Helper methods
    private String promptString(String message, Scanner scanner) {
        System.out.print(message + ": ");
        return scanner.nextLine();
    }

    private void updateField(String fieldName, Consumer<String> setter, Scanner scanner) {
        String newValue = promptString("Enter new " + fieldName, scanner);
        setter.accept(newValue);
    }

    private void updateIntField(String fieldName, IntConsumer setter, Scanner scanner) {
        try {
            int newValue = Integer.parseInt(promptString("Enter new " + fieldName, scanner));
            setter.accept(newValue);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }
}
