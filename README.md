# Library Management System Terminal

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

```text
src/
├── Main.java                             # Application Entry Point
│   └── Initializes CLI Scanner, calls IniciateLoginClass.start()
│   └── Inspects logged-in role (Student, Faculty, Admin) and routes to UserMain or AdminMain
│
├── Main/                                 # Role-Based CLI Controllers
│   ├── UserMain.java                     # CLI Controller for Members (Students & Faculty)
│   │   └── studentAction(): Menu for Borrow, Reserve, Cart, Renew, View Summary, Feedback
│   │   └── facultyAction(): Menu for same actions + Requesting new books
│   │
│   └── AdminMain.java                    # CLI Controller for Admins
│       └── User Action Menu (Add, Update, Delete users, View Blocked Users, Apply Fines)
│       └── Book Action Menu (Add, Update, Delete books, View system-wide borrowed/reserved/cart)
│       └── Mail Menu (Batch send renewal, return, overdue, blocked user emails)
│       └── Others Menu (Review member feedbacks & faculty book requests)
│
├── Users/                                # User Models & User Management Logic
│   ├── LibraryUser.java                  # Base Data Model for all Library Users
│   │   └── Fields: userId, username, password, type, contactInfo (Getters/Setters + ANSI toString)
│   │
│   ├── Student.java                      # Subclass of LibraryUser
│   │   └── Extends LibraryUser with batch (YYYY) and year (1-4)
│   │
│   ├── Faculty.java                      # Subclass of LibraryUser
│   │   └── Extends LibraryUser with department (CSE, IT, ECE, MECH, etc.)
│   │
│   ├── BlockedUser.java                  # Data Model & Loader for Overdue Blocked Members
│   │   └── Scans BorrowedBooks.csv for renewalCount == -2, writes to BlockedUsers.csv
│   │
│   ├── ManageUsersDetails.java           # Data Access Layer (DAO) for User Files
│   │   └── Reads/Writes Student.csv & Faculty.csv using CSVReader/CSVWriter
│   │   └── Provides searchUserById(), addNewStudent(), removeFaculty(), updateUserDetails()
│   │
│   └── LibraryUserManager.java           # Terminal Controller for Admin User Management
│       └── Uses ManageUsersDetails via Composition to execute Add, Delete, and Update workflows
│       └── Uses Method References (userToUpdate::setUsername) to dynamically update fields
│
├── Books/                                # Book Models & Book Management Logic
│   ├── Book.java                         # Data Model for Books
│   │   └── Fields: isbn, title, author, publisher, bookGroup, quantities, rowName, rackNo, borrowCount
│   │
│   ├── ManageBooksDetails.java           # Data Access Layer (DAO) for Books.csv
│   │   └── Loads/writes books to Books.csv with duplicate ISBN checks
│   │
│   ├── LibraryBookManager.java           # Terminal Controller for Admin Book Operations
│   │   └── CLI prompts for adding, updating, and deleting books with ValidationUtils validation
│   │
│   ├── BookRequest.java                  # Model & Display Helper for Faculty Book Requests
│   │   └── Fields: userId, bookDetails, requestReason; writes to RequestBooks.csv
│   │
│   └── Feedback.java                     # Model & Display Helper for User Feedback
│       └── Fields: userId, date, feedback; writes user suggestions to Feedback.csv
│
├── Login/                                # Authentication & Security Subsystem
│   ├── User.java                         # Base Authentication Model (userId, username, password)
│   │
│   ├── Login.java                        # Interface defining login(userId, password) & logout()
│   │
│   ├── Admin.java                        # Extends User, implements Login for Admin role
│   │
│   ├── LibraryMember.java                # Extends User, implements Login for Student/Faculty roles
│   │
│   ├── LoginSystem.java                  # Polymorphic Login Execution Engine
│   │   └── Runs user.login() polymorphically; triggers SecurityTokenCheck if user is Admin
│   │
│   ├── IniciateLoginClass.java           # Master Login Workflow Coordinator
│   │   └── Terminal menus for Admin vs Member login; enforces max attempts & blocked user checks
│   │
│   ├── SignUp.java                       # Member Self-Registration Controller
│   │   └── Prompts inputs, validates format, triggers OTP email verification, writes user to CSV
│   │
│   ├── OTPManager.java                   # Low-Level OTP Lifecycle Handler
│   │   └── Generates 6-digit random code, saves to Databases/Otp.txt, verifies, wipes file after use
│   │
│   ├── OTPVerification.java              # OTP Email Orchestrator
│   │   └── Triggers OTPManager and calls Mail.sendOTPVerificationMail()
│   │
│   ├── OTPHandler.java                   # CLI Helper for OTP Retries
│   │   └── Gives user 3 attempts to enter the email OTP in terminal
│   │
│   └── SecurityTokenCheck.java           # Secondary Security Verification
│       └── Requires entering token 'secureAdmin123' for sensitive admin/write operations
│
├── UserActions/                          # Member Actions (Borrow, Reserve, Cart, Renew, Fine)
│   ├── BorrowedBooks.java                # Data Model for Borrowed Records
│   │   └── Fields: userId, bookId, checkOutDate, renewalDate, returnDate, renewalCount, fineAmount
│   │
│   ├── BorrowedBooksService.java         # Core Business Logic for Borrowed Books
│   │   └── Calculates real-time fines (₹10/₹25 per overdue period), handles book returns & renewals
│   │
│   ├── Reservation.java                  # Data Model for Reserved Books (pickup deadline 3 days)
│   │
│   ├── CartCollection.java               # Data Model for User Shopping Cart Books
│   │   
│   ├── UserActionValidator.java          # Business Limits Enforcer
│   │   └── Enforces Max Borrowed Books (7), Max Reservations (5), Max Cart Items (5)
│   │
│   ├── UserActionService.java            # Record Creation Service
│   │   └── Generates new BorrowedBooks, Reservation, and CartCollection entries with automatic dates
│   │
│   ├── UserActionManager.java            # Terminal Coordinator for User Borrow/Reserve/Cart Workflow
│   │   └── Checks user eligibility via UserActionValidator, prompts book selection, updates stock
│   │
│   ├── UserActionHandler.java            # Non-borrow action executor (Account summary, Feedback, Requests)
│   │
│   ├── ViewAccountSummary.java           # Formatted CLI Table Viewer for individual user account summary
│   │
│   ├── LibraryAccountSummary.java        # System-wide summary viewer for Admin (all borrowed/reserved/cart)
│   │
│   └── LibraryDataCleaner.java           # Background Database Cleanup Task
│       └── Deletes expired reservations (> pickup deadline) and old cart items (> 30 days)
│
├── SearchQuerys/                         # Dynamic Query Engines
│   ├── SearchBooks.java                  # Book Search Engine using Java Streams
│   │   └── Search by title, author, ISBN, publisher, group, row, rack, available quantity
│   │
│   ├── SearchUsers.java                  # User Search Engine using Bounded Generics (<T extends LibraryUser>)
│   │   └── Searches Students or Faculty by username, ID, batch, year, contact info, department
│   │
│   ├── InitiateSearchBookFunctions.java  # Terminal UI Menu for Book Searches
│   │
│   └── InitiateSearchUserFunctions.java  # Terminal UI Menu for User Searches
│
├── Mail/                                 # Email Notification Subsystem (JavaMail API / SMTP)
│   ├── Mail.java                         # SMTP Client Engine
│   │   └── Configures TLS (port 587), sends HTML formatted emails via Gmail SMTP
│   │   └── Functions: sendRenewalMail(), sendReturnMail(), sendOverdueMail(), sendBlockedUserMail()
│   │
│   └── UserNotificationService.java      # Batch Email Alert Manager
│       └── Scans BorrowedBooks.csv for approaching due dates/overdues and sends bulk alerts
│
├── FileFunction/                         # Custom Generic Database Persistence Layer
│   ├── CSVReader.java                    # Generic CSV File Reader
│   │   └── Uses Java Reflection API & System.arraycopy to parse CSV rows directly into Java Objects
│   │
│   ├── CSVWriter.java                    # Generic CSV File Writer
│   │   └── Inspects fields via Reflection and serializes lists of Java Objects back to pipe-delimited CSV
│   │
│   └── MalformedCSVException.java        # Custom Exception for corrupted CSV formatting
│
├── DateUtils/                            # Date Utilities
│   └── DateUtils.java                    # Calculates renewal dates (+7 days), return dates (+28 days), pickup deadlines (+3 days)
│
└── InputValidationUtils/                 # Input Validation Framework
    └── ValidationUtils.java              # RegEx rules for Email, ISBN, Passwords, IDs + @FunctionalInterface InputValidator<T>
```


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
