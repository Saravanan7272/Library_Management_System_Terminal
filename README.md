# Library Management System

The Library Management System (LMS) is a Java-based application designed to facilitate efficient library management, supporting roles such as Admin, Student, and Faculty. It provides features for book management, user management, fine handling, and secure access.

## Table of Contents
1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Package Structure and Class Overview](#package-structure-and-class-overview)
   - [1. Users Package](#1-users-package)
   - [2. Books Package](#2-books-package)
   - [3. Login Package](#3-login-package)
   - [4. Mail Package](#4-mail-package)
   - [5. SearchQueries Package](#5-searchqueries-package)
   - [6. UserActions Package](#6-useractions-package)
   - [7. Utilities Package](#7-utilities-package)
4. [Class Diagram](#class-diagram)
5. [Installation and Setup](#installation-and-setup)
   - [Prerequisites](#prerequisites)
   - [Installation](#installation)
6. [Compilation and Execution](#compilation-and-execution)
   - [Using an IDE (e.g., Eclipse, IntelliJ IDEA)](#using-an-ide-eg-eclipse-intellij-idea)
   - [Using Command Line](#using-command-line)
7. [Usage](#usage)
8. [Key Class Operations](#key-class-operations)
9. [Object-Oriented Concepts Used](#object-oriented-concepts-used)
10. [Future Enhancements](#future-enhancements)
11. [Contributing](#contributing)
12. [License](#license)
13. [Acknowledgments](#acknowledgments)

---

## Project Overview

The LMS is a Java-based application designed to facilitate efficient library management with roles such as Admin, Student, and Faculty. Admin users have advanced capabilities like managing books, handling blocked users, and sending notifications. The system implements two-factor authentication and various security protocols to ensure data integrity and user privacy.

## Features

- **Secure User Authentication**: Two-factor authentication (OTP-based) for secure login.
- **Comprehensive User and Book Management**: Admins can manage users and books, while members can search, borrow, renew, and reserve books.
- **Manual Notification System**: Admins can send notifications for renewals, returns, and overdue reminders.
- **Real-Time Fine Management**: Overdue fines are calculated and updated in real time.
- **Advanced Book Browsing**: Members can search for books by title, author, genre, ISBN, and more.
- **Feedback Management**: Members can submit feedback for Admin review.

## Package Structure and Class Overview

### 1. Users Package
**Location**: `src/Users`  
**Description**: Manages different user types and related data.  
**Classes**:  
- `BlockedUser`: Tracks users blocked due to overdue books.
- `Faculty` and `Student`: Represent faculty and student users, extending `LibraryUser`.
- `LibraryUserManager`: Manages user operations (add, delete, update).
- `ManageUserDetails`: Handles user detail management.

### 2. Books Package
**Location**: `src/Books`  
**Description**: Manages all book-related information.  
**Classes**:  
- `Book`: Represents a book’s data.
- `LibraryBookManager`: Handles adding, removing, and updating book records.
- `ReservedBook`: Manages book reservation information.

### 3. Login Package
**Location**: `src/Login`  
**Description**: Manages authentication and security.  
**Classes**:  
- `Admin`: Admin user with privileges for system management.
- `InitiateLoginClass`: Initializes the login process.
- `OTPHandler`, `OTPVerification`, `SecurityTokenCheck`: Manage OTP verification and secure login.

### 4. Mail Package
**Location**: `src/Mail`  
**Description**: Handles email notifications.  
**Classes**:  
- `Mail`: Manages email alerts for overdue and renewal reminders.
- `UserNotificationService`: Sends various notifications to users.

### 5. SearchQueries Package
**Location**: `src/SearchQueries`  
**Description**: Implements searching functionality for books and users.  
**Classes**:  
- `SearchBooks`: Allows users to search by title, author, ISBN, etc.
- `SearchUsers`: Admin function to search for users.

### 6. UserActions Package
**Location**: `src/UserActions`  
**Description**: Manages actions like borrowing, reserving, and providing feedback.  
**Classes**:  
- `BorrowedBooks`, `Reservation`: Track borrowed and reserved books.
- `UserActionHandler`: Handles member actions.
- `UserActionValidator`: Validates member permissions and actions.

### 7. Utilities Package
**Description**: Commonly used utilities across packages.  
**Classes**:  
- `CSVReader`, `CSVWriter`: Handle data persistence using CSV files.
- `DateUtils`: Manages date functions for book due dates and fines.
- `ValidationUtils`: Validates user inputs like email and book titles.

## Class Diagram
"Please refer to the **Class Diagram PDF** attached in the ZIP file, which illustrates the relationships and interactions between the major classes within the system."

## Installation and Setup

### Prerequisites

1. **Java Development Kit (JDK)**
   - Ensure JDK 8 or above is installed.
   - Confirm installation with:
     ```bash
     java -version
     ```

2. **Dependencies**
   - The system requires external libraries for email functionalities. Download and place the following JAR files in a `libs` folder:
     - `javax.mail.jar`
     - `activation-1.1.1.jar`

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/LibraryManagementSystem.git
   cd LibraryManagementSystem

# Library Management System

## Add Dependencies
- Place the downloaded `javax.mail.jar` and `activation-1.1.1.jar` files in a `libs` folder within the project root.

## Compilation and Execution

### Using an IDE (e.g., Eclipse, IntelliJ IDEA)

1. **Import Project**: Open the project in your preferred IDE as a Java project.
2. **Add JAR Files**:
   - **In Eclipse**: Right-click the project > `Build Path` > `Configure Build Path` > Add the JARs under the `libs` folder.
   - **In IntelliJ IDEA**: Go to `File` > `Project Structure` > `Modules` > `Dependencies`, and add the JAR files.
3. **Run Main Class**: Set `Main` as the main class in the IDE's configuration, then run the project.

### Using Command Line

1. **Compile the Project**:
   ```bash
   javac -cp "libs/*" -d out src/**/*.java
-This command compiles .java files in the src folder, outputting .class files to an out folder.

2. **Run the Project**:
   ```bash
   java -cp "out:libs/*" Main.Main
-This command runs the project using the compiled .class files from the out folder and the necessary JAR files from the libs folder.

## Usage
### User Roles and Functions

#### 1. Admin Functions:
- Manage books and users (add, update, delete).
- Send manual notifications for renewals and returns.
- Review feedback submitted by members.

#### 2. Member Functions:
- Search for and borrow books.
- Manage personal cart and reservation records.
- Submit feedback to admins.

### Key Class Operations
- **User Management**: Handled by classes within the `Users` package, allowing Admins to create, modify, and delete user accounts.
- **Book Management**: Managed by the `Books` package classes, which provide functions for adding, removing, and reserving books.
- **Notification System**: Implemented in the `Mail` package, providing essential communication for book renewals, due dates, and blocked user notifications.

### Object-Oriented Concepts Used
- **Encapsulation**: Data is protected within classes and accessed only through getter and setter methods.
- **Inheritance**: Student and Faculty inherit properties and methods from the `LibraryUser` base class.
- **Polymorphism**: Allows objects of different types (Admin, Faculty, Student) to be treated as instances of their parent class, `LibraryUser`.
- **Abstraction**: Hides complex OTP verification and email-sending functions behind simple interfaces.
- **Composition and Aggregation**: Strong associations where objects like `Reservation` depend on `Book` instances, with objects aggregated in lists for easier management.

### Future Enhancements
## Future Enhancements
- **User Interface (UI)**: Adding a graphical user interface to improve accessibility.
- **Automated Notifications**: Automate overdue and renewal alerts to reduce manual effort.
- **Online Fine Payments**: Enable users to settle fines online for added convenience.
- **Cloud Integration**: Real-time, cloud-based data sharing for multiple users.
- **Mobile Compatibility**: Make the system accessible on mobile devices.
- **Real-time Chat/Help Desk**: Implement a real-time chat or help desk feature for users to get immediate assistance.
- **Dashboard & Analytics**: Add a dashboard to provide an overview of user activity, book statistics, and system performance.
- **Book Review and Rating System**: Allow users to review and rate books they borrow, helping others in their decision-making process.
- **Recommendation System**: Develop a recommendation engine to suggest books to users based on their reading history and preferences.

## Contact Information

For support or inquiries, feel free to reach out at:

- **Email**: support@example.com
- **GitHub Issues**: [LibraryManagementSystem Issues](https://github.com/yourusername/LibraryManagementSystem/issues)
- **Project Repository**: [LibraryManagementSystem Repository](https://github.com/yourusername/LibraryManagementSystem)

---

## Thank You

Thank you for checking out the Library Management System! We appreciate your interest in our project. If you have any feedback, suggestions, or issues, don't hesitate to reach out or open an issue on GitHub. Your support helps us improve and grow the project.

Special thanks to everyone who contributed to this project and helped in its development.

