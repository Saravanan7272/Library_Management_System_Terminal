package Users;

import Books.*;
import DateUtils.*;
import FileFunction.*;
import Login.*;
import Mail.*;
import SearchQuerys.*;
import UserActions.*;
import java.io.*;
import java.util.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
//public class ManageUsersDetails {
//
//    private static final String STUDENT_FILE_PATH = "Databases/Students.csv";
//    private static final String FACULTY_FILE_PATH = "Databases/Faculty.csv";
//
//    // Method to load all students from the CSV file
//    public List<Student> loadStudentsFromCSV() throws Exception {
//        return CSVReader.readCSV(STUDENT_FILE_PATH, Student.class);
//    }
//
//    // Method to load all faculty from the CSV file
//    public List<Faculty> loadFacultyFromCSV() throws Exception {
//        return CSVReader.readCSV(FACULTY_FILE_PATH, Faculty.class);
//    }
//
//    // Method to write students to the CSV file
//    public void writeStudentsToCSV(List<Student> students) throws Exception {
//        CSVWriter.writeObjectsToCSV(STUDENT_FILE_PATH, students, 1);
//    }
//
//    // Method to write faculty to the CSV file
//    public void writeFacultyToCSV(List<Faculty> faculty) throws Exception {
//        CSVWriter.writeObjectsToCSV(FACULTY_FILE_PATH, faculty, 1);
//    }
//
//    // Add a new student
//    public void addNewStudent(Student newStudent) throws Exception {
//        List<Student> students = loadStudentsFromCSV();
//
//        // Check if a student with the same user ID already exists
//        boolean studentExists = students.stream()
//                .anyMatch(student -> student.getUserId().equals(newStudent.getUserId()));
//
//        if (studentExists) {
//            throw new Exception("Error: A student with user ID " + newStudent.getUserId() + " already exists.");
//        }
//
//        // Add the new student to the list if it does not exist
//        CSVWriter.writeObjectsToCSV(STUDENT_FILE_PATH, students, 0);
//    }
//
//    // Remove a student by matching their user ID
//    public void removeStudent(Student studentToRemove) throws Exception {
//        List<Student> students = loadStudentsFromCSV();
//
//        // Check if the student to remove exists in the list
//        boolean studentExists = students.stream()
//                .anyMatch(student -> student.getUserId().equals(studentToRemove.getUserId()));
//
//        if (!studentExists) {
//            throw new Exception("Error: The student with user ID " + studentToRemove.getUserId() + " does not exist.");
//        }
//
//        // Filter out the student to be removed based on user ID
//        List<Student> updatedStudents = students.stream()
//                .filter(student -> !student.getUserId().equals(studentToRemove.getUserId()))
//                .collect(Collectors.toList());
//
//        writeStudentsToCSV(updatedStudents);
//    }
//
//    // Update the details of a student based on their user ID
//    public void updateStudentDetails(Student updatedStudent) throws Exception {
//        List<Student> students = loadStudentsFromCSV();
//
//        // Check if the student to update exists in the list
//        boolean studentExists = students.stream()
//                .anyMatch(student -> student.getUserId().equals(updatedStudent.getUserId()));
//
//        if (!studentExists) {
//            throw new Exception("Error: The student with user ID " + updatedStudent.getUserId() + " does not exist.");
//        }
//
//        // Update the student details if it exists in the list
//        List<Student> updatedStudents = students.stream()
//                .map(student -> student.getUserId().equals(updatedStudent.getUserId()) ? updatedStudent : student)
//                .collect(Collectors.toList());
//
//        writeStudentsToCSV(updatedStudents);
//    }
//
//    // Add a new faculty member
//    public void addNewFaculty(Faculty newFaculty) throws Exception {
//        List<Faculty> faculty = loadFacultyFromCSV();
//
//        // Check if a faculty member with the same user ID already exists
//        boolean facultyExists = faculty.stream()
//                .anyMatch(facultyMember -> facultyMember.getUserId().equals(newFaculty.getUserId()));
//
//        if (facultyExists) {
//            throw new Exception("Error: A faculty member with user ID " + newFaculty.getUserId() + " already exists.");
//        }
//
//        // Add the new faculty member to the list if it does not exist
//        CSVWriter.writeObjectsToCSV(FACULTY_FILE_PATH, faculty, 0);
//    }
//
//    // Remove a faculty member by matching their user ID
//    public void removeFaculty(Faculty facultyToRemove) throws Exception {
//        List<Faculty> faculty = loadFacultyFromCSV();
//
//        // Check if the faculty member to remove exists in the list
//        boolean facultyExists = faculty.stream()
//                .anyMatch(facultyMember -> facultyMember.getUserId().equals(facultyToRemove.getUserId()));
//
//        if (!facultyExists) {
//            throw new Exception("Error: The faculty member with user ID " + facultyToRemove.getUserId() + " does not exist.");
//        }
//
//        // Filter out the faculty member to be removed based on user ID
//        List<Faculty> updatedFaculty = faculty.stream()
//                .filter(facultyMember -> !facultyMember.getUserId().equals(facultyToRemove.getUserId()))
//                .collect(Collectors.toList());
//
//        writeFacultyToCSV(updatedFaculty);
//    }
//
//    // Update the details of a faculty member based on their user ID
//    public void updateFacultyDetails(Faculty updatedFaculty) throws Exception {
//        List<Faculty> faculty = loadFacultyFromCSV();
//
//        // Check if the faculty member to update exists in the list
//        boolean facultyExists = faculty.stream()
//                .anyMatch(facultyMember -> facultyMember.getUserId().equals(updatedFaculty.getUserId()));
//
//        if (!facultyExists) {
//            throw new Exception("Error: The faculty member with user ID " + updatedFaculty.getUserId() + " does not exist.");
//        }
//
//        // Update the faculty member details if they exist in the list
//        List<Faculty> updatedFacultyList = faculty.stream()
//                .map(facultyMember -> facultyMember.getUserId().equals(updatedFaculty.getUserId()) ? updatedFaculty : facultyMember)
//                .collect(Collectors.toList());
//
//        writeFacultyToCSV(updatedFacultyList);
//    }
//}
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ManageUsersDetails {

    private static final String STUDENT_FILE_PATH = "Databases/Student.csv";
    private static final String FACULTY_FILE_PATH = "Databases/Faculty.csv";

    // Method to load all students from the CSV file
    public List<Student> loadStudentsFromCSV() throws Exception {
        return CSVReader.readCSV(STUDENT_FILE_PATH, Student.class);
    }

    // Method to load all faculty from the CSV file
    public List<Faculty> loadFacultyFromCSV() throws Exception {
        return CSVReader.readCSV(FACULTY_FILE_PATH, Faculty.class);
    }

    // Write a list of students to the CSV file
    private void writeStudentsToCSV(List<Student> students) throws Exception {
        CSVWriter.writeObjectsToCSV(STUDENT_FILE_PATH, students, 1);
    }

    // Write a list of faculty to the CSV file
    private void writeFacultyToCSV(List<Faculty> faculty) throws Exception {
        CSVWriter.writeObjectsToCSV(FACULTY_FILE_PATH, faculty, 1);
    }

    // Add a new student if user ID is unique
    public void addNewStudent(Student newStudent) throws Exception {
        List<Student> students = loadStudentsFromCSV();
        if (isUserExists(students, newStudent.getUserId())) {
            throw new Exception("Error: A student with user ID " + newStudent.getUserId() + " already exists.");
        }
        students.add(newStudent);
        writeStudentsToCSV(students);
    }

    // Add a new faculty member if user ID is unique
    public void addNewFaculty(Faculty newFaculty) throws Exception {
        List<Faculty> faculty = loadFacultyFromCSV();
        if (isUserExists(faculty, newFaculty.getUserId())) {
            throw new Exception("Error: A faculty member with user ID " + newFaculty.getUserId() + " already exists.");
        }
        faculty.add(newFaculty);
        writeFacultyToCSV(faculty);
    }

    // Remove a student by user ID
    public void removeStudent(Student studentToRemove) throws Exception {
        List<Student> students = loadStudentsFromCSV();
        if (!isUserExists(students, studentToRemove.getUserId())) {
            throw new Exception("Error: The student with user ID " + studentToRemove.getUserId() + " does not exist.");
        }
        List<Student> updatedStudents = filterOutUser(students, studentToRemove.getUserId());
        writeStudentsToCSV(updatedStudents);
    }

    // Remove a faculty member by user ID
    public void removeFaculty(Faculty facultyToRemove) throws Exception {
        List<Faculty> faculty = loadFacultyFromCSV();
        if (!isUserExists(faculty, facultyToRemove.getUserId())) {
            throw new Exception("Error: The faculty member with user ID " + facultyToRemove.getUserId() + " does not exist.");
        }
        List<Faculty> updatedFaculty = filterOutUser(faculty, facultyToRemove.getUserId());
        writeFacultyToCSV(updatedFaculty);
    }

    // Update user details by user ID
    public void updateUserDetails(LibraryUser updatedUser) throws Exception {
        if (updatedUser instanceof Student) {
            List<Student> students = loadStudentsFromCSV();
            List<Student> updatedStudents = updateUserDetailsInList(students, (Student) updatedUser);
            writeStudentsToCSV(updatedStudents);
        } else if (updatedUser instanceof Faculty) {
            List<Faculty> faculty = loadFacultyFromCSV();
            List<Faculty> updatedFaculty = updateUserDetailsInList(faculty, (Faculty) updatedUser);
            writeFacultyToCSV(updatedFaculty);
        }
    }

    // Helper method to update details of a specific user in a list
    private <T extends LibraryUser> List<T> updateUserDetailsInList(List<T> users, T updatedUser) {
        return users.stream()
                .map(user -> user.getUserId().equals(updatedUser.getUserId()) ? updatedUser : user)
                .collect(Collectors.toList());
    }

    // Search for a student by user ID
    public Student searchStudentById(String userId) throws Exception {
        return loadStudentsFromCSV().stream()
                .filter(student -> student.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    // Search for a faculty member by user ID
    public Faculty searchFacultyById(String userId) throws Exception {
        return loadFacultyFromCSV().stream()
                .filter(faculty -> faculty.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    // General search for a user by ID (returns LibraryUser)
    public LibraryUser searchUserById(String userId) throws Exception {
        LibraryUser user = searchStudentById(userId);
        return (user != null) ? user : searchFacultyById(userId);
    }

    // Helper method to check if a user with a specific ID exists in the list
    private <T extends LibraryUser> boolean isUserExists(List<T> users, String userId) {
        return users.stream().anyMatch(user -> user.getUserId().equals(userId));
    }

    // Helper method to filter out a user by user ID
    private <T extends LibraryUser> List<T> filterOutUser(List<T> users, String userId) {
        return users.stream()
                .filter(user -> !user.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
