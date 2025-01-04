package SearchQuerys;
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
import java.util.List;

import FileFunction.CSVReader;
import Users.Faculty;
import Users.Student;

import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//public class SearchUsers {
//
//    private List<Student> students;
//    private List<Faculty> faculty;
//
//    // Constructor to initialize the lists and load data
//    public SearchUsers() {
//        students = new ArrayList<>();
//        faculty = new ArrayList<>();
//        loadStudents();
//        loadFaculty();
//    }
//
//    // Method to load student data from a CSV file
//    private void loadStudents() {
//        try {
//            students = CSVReader.readCSV("Databases/Student.csv", Student.class);
//        } catch (FileNotFoundException e) {
//            System.out.println("Student data file not found.");
//            students = new ArrayList<>();
//        } catch (IOException e) {
//            System.out.println("Error reading student data.");
//            students = new ArrayList<>();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // Method to load faculty data from a CSV file
//    private void loadFaculty() {
//        try {
//            faculty = CSVReader.readCSV("Databases/Faculty.csv", Faculty.class);
//        } catch (FileNotFoundException e) {
//            System.out.println("Faculty data file not found.");
//            faculty = new ArrayList<>();
//        } catch (IOException e) {
//            System.out.println("Error reading faculty data.");
//            faculty = new ArrayList<>();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // Search for students based on multiple optional filters
//    public List<Student> searchStudents(String username, String userId, String batch, Integer year, String contactInfo) {
//        return searchUsers(students, username, userId, batch, year, contactInfo);
//    }
//
//    // Search for faculty based on multiple optional filters
//    public List<Faculty> searchFaculty(String username, String userId, String department, String contactInfo) {
//        return searchUsers(faculty, username, userId, department, contactInfo);
//    }
//
//    // Generic method to search users (both student and faculty) based on given filters
//    private List<Student> searchUsers(List<Student> list, String username, String userId, String batch, Integer year, String contactInfo) {
//        List<Student> result = new ArrayList<>();
//
//        for (Student user : list) {
//            boolean match = true;
//
//            // Check username if provided
//            if (username != null && !username.isEmpty() && !getUsername(user).equals(username)) {
//                match = false;
//            }
//
//            // Check userId if provided
//            if (userId != null && !userId.isEmpty() && !getUserId(user).equals(userId)) {
//                match = false;
//            }
//
//            // Check batch if provided
//            if (batch != null && !batch.isEmpty() && !getBatch(user).equals(batch)) {
//                match = false;
//            }
//
//            // Check year if provided
//            if (year != null && !year.equals(getYear(user))) {
//                match = false;
//            }
//
//            // Check contactInfo if provided
//            if (contactInfo != null && !contactInfo.isEmpty() && !getContactInfo(user).equals(contactInfo)) {
//                match = false;
//            }
//
//            if (match) {
//                result.add(user);
//            }
//        }
//
//        return result;
//    }
//
//    // Overloaded method to search faculty based on given filters
//    private List<Faculty> searchUsers(List<Faculty> list, String username, String userId, String department, String contactInfo) {
//        List<Faculty> result = new ArrayList<>();
//
//        for (Faculty user : list) {
//            boolean match = true;
//
//            // Check username if provided
//            if (username != null && !username.isEmpty() && !getUsername(user).equals(username)) {
//                match = false;
//            }
//
//            // Check userId if provided
//            if (userId != null && !userId.isEmpty() && !getUserId(user).equals(userId)) {
//                match = false;
//            }
//
//            // Check department if provided
//            if (department != null && !department.isEmpty() && !getDepartment(user).equals(department)) {
//                match = false;
//            }
//
//            // Check contactInfo if provided
//            if (contactInfo != null && !contactInfo.isEmpty() && !getContactInfo(user).equals(contactInfo)) {
//                match = false;
//            }
//
//            if (match) {
//                result.add(user);
//            }
//        }
//
//        return result;
//    }
//
//    // Specific search methods for Student
//    public List<Student> searchByUsername(String username) {
//        return searchStudents(username, null, null, null, null);
//    }
//
//    public List<Student> searchByUserId(String userId) {
//        return searchStudents(null, userId, null, null, null);
//    }
//
//    public List<Student> searchByYear(Integer year) {
//        return searchStudents(null, null, null, year, null);
//    }
//
//    // Specific search methods for Faculty
//    public List<Faculty> searchFacultyByUsername(String username) {
//        return searchFaculty(username, null, null, null);
//    }
//
//    public List<Faculty> searchFacultyByDepartment(String department) {
//        return searchFaculty(null, null, department, null);
//    }
//
//    // New method to search faculty by contact info (phone/email)
//    public List<Faculty> searchFacultyByContactInfo(String contactInfo) {
//        return searchFaculty(null, null, null, contactInfo);
//    }
//
//    // Helper methods to get attributes from different user types
//    private String getUsername(Object user) {
//        if (user instanceof Student) {
//            return ((Student) user).getUsername();
//        } else if (user instanceof Faculty) {
//            return ((Faculty) user).getUsername();
//        }
//        return "";
//    }
//
//    private String getUserId(Object user) {
//        if (user instanceof Student) {
//            return ((Student) user).getUserId();
//        } else if (user instanceof Faculty) {
//            return ((Faculty) user).getUserId();
//        }
//        return "";
//    }
//
//    private String getBatch(Object user) {
//        if (user instanceof Student) {
//            return ((Student) user).getBatch();
//        }
//        return "";
//    }
//
//    private Integer getYear(Object user) {
//        if (user instanceof Student) {
//            return ((Student) user).getYear();
//        }
//        return null;
//    }
//
//    private String getContactInfo(Object user) {
//        if (user instanceof Student) {
//            return ((Student) user).getContactInfo();
//        } else if (user instanceof Faculty) {
//            return ((Faculty) user).getContactInfo();
//        }
//        return "";
//    }
//
//    private String getDepartment(Faculty user) {
//        return user.getDepartment();
//    }
//    // Method to get contact info (emails) by a list of user IDs
//    public List<String> getContactInfoByUserIds(List<String> userIds) {
//        return userIds.stream()
//                .flatMap(userId -> {
//                    // Check for student contact info
//                    Optional<String> studentContactInfo = students.stream()
//                            .filter(student -> student.getUserId().equalsIgnoreCase(userId))
//                            .map(Student::getContactInfo)
//                            .findFirst();
//
//                    // If student info is not found, check faculty
//                    if (studentContactInfo.isPresent()) {
//                        return Stream.of(studentContactInfo.get()); // Return the found student email
//                    } else {
//                        return faculty.stream()
//                                .filter(facultyMember -> facultyMember.getUserId().equalsIgnoreCase(userId))
//                                .map(Faculty::getContactInfo); // Return the faculty email if found
//                    }
//                })
//                .collect(Collectors.toList()); // Collect all emails into a list
//    }
//}


public class SearchUsers {

    private List<Student> students;
    private List<Faculty> faculty;

    // Constructor to initialize the lists and load data
    public SearchUsers() {
        students = new ArrayList<>();
        faculty = new ArrayList<>();
        loadStudents();
        loadFaculty();
    }

    // Method to load student data from a CSV file
    private void loadStudents() {
        try {
            students = CSVReader.readCSV("Databases/Student.csv", Student.class);
        } catch (FileNotFoundException e) {
            System.out.println("Student data file not found.");
            students = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error reading student data.");
            students = new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Method to load faculty data from a CSV file
    private void loadFaculty() {
        try {
            faculty = CSVReader.readCSV("Databases/Faculty.csv", Faculty.class);
        } catch (FileNotFoundException e) {
            System.out.println("Faculty data file not found.");
            faculty = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error reading faculty data.");
            faculty = new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Common search method for both Students and Faculty
    public <T extends LibraryUser> List<T> searchUsers(List<T> users, String username, String userId, String batch, Integer year, String contactInfo, String department) {
        return users.stream()
                .filter(user -> matchesFilter(user, username, userId, batch, year, contactInfo, department))
                .collect(Collectors.toList());
    }

    // Matches the filter criteria for both Students and Faculty
    private boolean matchesFilter(LibraryUser user, String username, String userId, String batch, Integer year, String contactInfo, String department) {
        if (username != null && !user.getUsername().equals(username)) {
            return false;
        }
        if (userId != null && !user.getUserId().equals(userId)) {
            return false;
        }
        if (contactInfo != null && !user.getContactInfo().equals(contactInfo)) {
            return false;
        }
        if (user instanceof Student) {
            Student student = (Student) user;
            if (batch != null && !student.getBatch().equals(batch)) {
                return false;
            }
            if (year != null && student.getYear() != year) {
                return false;
            }
        }
        if (user instanceof Faculty) {
            Faculty facultyMember = (Faculty) user;
            if (department != null && !facultyMember.getDepartment().equals(department)) {
                return false;
            }
        }
        return true;
    }

    // Search methods for Students (by common fields + unique fields like batch and year)
    public List<Student> searchStudents(String username, String userId, String batch, Integer year, String contactInfo) {
        return searchUsers(students, username, userId, batch, year, contactInfo, null);
    }

    // Search methods for Faculty (by common fields + unique fields like department)
    public List<Faculty> searchFaculty(String username, String userId, String department, String contactInfo) {
        return searchUsers(faculty, username, userId, null, null, contactInfo, department);
    }

        // Method to get contact info (emails) by a list of user IDs
    public List<String> getContactInfoByUserIds(List<String> userIds) {
        return userIds.stream()
                .flatMap(userId -> {
                    // Check for student contact info
                    Optional<String> studentContactInfo = students.stream()
                            .filter(student -> student.getUserId().equalsIgnoreCase(userId))
                            .map(Student::getContactInfo)
                            .findFirst();

                    // If student info is not found, check faculty
                    if (studentContactInfo.isPresent()) {
                        return Stream.of(studentContactInfo.get()); // Return the found student email
                    } else {
                        return faculty.stream()
                                .filter(facultyMember -> facultyMember.getUserId().equalsIgnoreCase(userId))
                                .map(Faculty::getContactInfo); // Return the faculty email if found
                    }
                })
                .collect(Collectors.toList()); // Collect all emails into a list
    }
    public boolean checkIfUserExists(String userId) {
        for (Student student : students) {
            if (student.getUserId().equals(userId)) {
                System.out.println("\u001B[31m"+"Student with User ID " + userId + " already exists."+"\u001B[0m");
                return true;
            }
        }
        for (Faculty facultyMember : faculty) {
            if (facultyMember.getUserId().equals(userId)) {
                System.out.println("Faculty with User ID " + userId + " already exists.");
                return true;
            }
        }

        return false;
    }
}
