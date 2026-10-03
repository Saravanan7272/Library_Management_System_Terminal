## ⚡ 1. The Ultimate OOP Cheat Sheet (Library Management System)

| OOP Concept | Simple Meaning | Real-World Analogy | Library Project Example (Your Codebase) | Specific Code Implementation Details |
| :--- | :--- | :--- | :--- | :--- |
| **Encapsulation** | Hiding private data & controlling access via getters, setters, and validation | Capsule / ATM Machine (you interact with buttons, not internal cash mechanisms) | Private fields in `LibraryUser`, `Book`, `BorrowedBooks` with getters/setters & validated mutations | • Private fields in [`LibraryUser.java`](file:///e:/Library_Management_System_Terminal/src/Users/LibraryUser.java#L12-L16) (`private String userId; private String password;`)<br>• Validation inside [`LibraryBookManager.java`](file:///e:/Library_Management_System_Terminal/src/Books/LibraryBookManager.java#L134-L149) prevents `availableQuantity` from exceeding `totalQuantity`. |
| **Inheritance** | **IS-A** relationship (subclass reuses superclass code) | Animal → Dog / Vehicle → Car | `Student` & `Faculty` inherit from `LibraryUser`. `Admin` & `LibraryMember` inherit from `User`. | • [`Student.java`](file:///e:/Library_Management_System_Terminal/src/Users/Student.java#L10): `public class Student extends LibraryUser`<br>• Subclass constructors call `super(userId, username, password, type, contactInfo)`.<br>• Avoids repeating common fields like `userId` & `username`. |
| **Polymorphism**<br>*(Runtime / Dynamic)* | Same method call, **different behavior at runtime** based on object type | Driver pressing accelerator (works differently on Gas, Electric, or Hybrid car) | `LoginSystem` calling `.login()` on either an `Admin` or `LibraryMember` object | • [`LoginSystem.java`](file:///e:/Library_Management_System_Terminal/src/Login/LoginSystem.java#L15): `performLogin(Login user, ...)` accepts `Login` interface. JVM dynamically executes `Admin.login()` or `LibraryMember.login()`.<br>• `toString()` overridden in `Student` and `Faculty`. |
| **Polymorphism**<br>*(Compile-Time & Generic)* | Method overloading & Bounded Generics | Calculator with multiple `add()` methods (`add(int, int)` vs `add(double, double)`) | Multi-criteria book search & Bounded Generic User Search | • [`SearchBooks.java`](file:///e:/Library_Management_System_Terminal/src/SearchQuerys/SearchBooks.java#L36): Overloaded `searchBooks()` signature.<br>• [`SearchUsers.java`](file:///e:/Library_Management_System_Terminal/src/SearchQuerys/SearchUsers.java#L291): `<T extends LibraryUser> List<T> searchUsers(List<T> users, ...)`. |
| **Abstraction** | Hiding complex implementation details, exposing only essential interfaces | Car Steering Wheel (you turn it without knowing rack-and-pinion physics) | `Login` interface, `@FunctionalInterface InputValidator`, and Generic CSV Data Engine | • [`Login.java`](file:///e:/Library_Management_System_Terminal/src/Login/Login.java#L4): Interface defining `login()` and `logout()`.<br>• [`ValidationUtils.java`](file:///e:/Library_Management_System_Terminal/src/InputValidationUtils/ValidationUtils.java#L155): `@FunctionalInterface InputValidator<T>`.<br>• `CSVReader.readCSV()` hides low-level file pointers & Reflection parsing. |
| **Composition** | **STRONG HAS-A** relationship (Child **cannot exist** without Parent lifecycle) | House → Room<br>Car → Engine | `LibraryUserManager` HAS-A `ManageUsersDetails`. `OTPVerification` HAS-A `OTPManager`. | • [`LibraryUserManager.java`](file:///e:/Library_Management_System_Terminal/src/Users/LibraryUserManager.java#L187): `private final ManageUsersDetails userManager = new ManageUsersDetails();`<br>If `LibraryUserManager` object is destroyed, `ManageUsersDetails` dies with it. |
| **Aggregation** | **WEAK HAS-A** relationship (Child **can exist independently** of Parent) | Department → Professor<br>Library → Book | `UserNotificationService` HAS-A `List<BorrowedBooks>`. `SearchUsers` HAS-A `List<Student>`. | • [`UserNotificationService.java`](file:///e:/Library_Management_System_Terminal/src/Mail/UserNotificationService.java#L130): Passes `List<BorrowedBooks>` into constructor. `BorrowedBooks` items exist independently in the system. |

---

## 📊 2. Core OOP Concept Comparison Tables

### A. Composition vs Aggregation (The Classic Trick Question)
| Feature | Composition (Strong HAS-A) | Aggregation (Weak HAS-A) |
| :--- | :--- | :--- |
| **Lifetime Dependence** | **Dependent**: If parent is deleted, child is destroyed. | **Independent**: Child exists even if parent is deleted. |
| **Real-world Example** | House → Room, Car → Engine | Department → Professor, Library → Book |
| **Code Base Example** | `LibraryUserManager` creates `ManageUsersDetails` internally. | `UserNotificationService` receives `List<BorrowedBooks>` from outside. |

### B. Abstraction vs Encapsulation
| Feature | Abstraction | Encapsulation |
| :--- | :--- | :--- |
| **Main Goal** | **Hiding complexity** (Focuses on *WHAT* the object does) | **Hiding data** (Focuses on *HOW* to protect data) |
| **How Achieved** | Interfaces (`Login`), Abstract classes, Functional interfaces | `private` variables, `public` getters/setters, validation |
| **Code Base Example** | `Login` interface hides login implementation details. | `private String password` in `LibraryUser.java`. |

### C. Method Overriding vs Method Overloading
| Feature | Method Overriding (Runtime Polymorphism) | Method Overloading (Compile-Time Polymorphism) |
| :--- | :--- | :--- |
| **Where it occurs** | Subclass redefines parent class method. | Same class, same method name, **different parameters**. |
| **Resolution Time** | Resolved at **Runtime** by JVM (Dynamic Dispatch). | Resolved at **Compile-Time** by Compiler. |
| **Code Base Example** | `Student.java` overrides `LibraryUser.toString()`. | `SearchBooks.java` has multiple `searchBooks()` methods with different parameters. |

---

## 🚀 3. Technical Deep-Dives (Top 3 Project Highlights)

### Feature 1: Generic Persistence Layer using Java Reflection (`CSVReader` & `CSVWriter`)
* **Problem**: Standard CSV parsers require writing manual parsing logic for every single entity (`Book`, `Student`, `Faculty`, `BorrowedBooks`, etc.).
* **Solution**: You created a generic reader method `<T> List<T> readCSV(String path, Class<T> clazz)` in [`CSVReader.java`](file:///e:/Library_Management_System_Terminal/src/FileFunction/CSVReader.java#L106):
  1. Inspects `clazz.getSuperclass()` and `clazz.getDeclaredFields()`, combining parent and child fields into a single array via `System.arraycopy`.
  2. Reads each line from file and splits by pipe (`|`).
  3. Instantiates the object reflectively via `clazz.getDeclaredConstructor().newInstance()`.
  4. Calls `field.setAccessible(true)` to bypass `private` access controls, converts data types (`int`, `double`, `String`), and injects values dynamically.

### Feature 2: Functional Validation Pipeline (`ValidationUtils`)
* **Problem**: Terminal input validation creates duplicated, deeply nested `while` loops across UI files.
* **Solution**: You created a higher-order prompt helper method in [`ValidationUtils.java`](file:///e:/Library_Management_System_Terminal/src/InputValidationUtils/ValidationUtils.java#L244) using a `@FunctionalInterface`:
  ```java
  @FunctionalInterface
  public interface InputValidator<T> {
      boolean isValid(T input);
  }
  ```
  At call sites, validation rules are passed directly as **Method References** (e.g., `ValidationUtils::isValidUserId`, `ValidationUtils::isValidIsbn`).

### Feature 3: Two-Factor Authentication (2FA) & Real-Time SMTP Email Subsystem
* **Flow**:
  1. [`OTPManager.java`](file:///e:/Library_Management_System_Terminal/src/Login/OTPManager.java#L20) generates a 6-digit random code (`100000 + random.nextInt(900000)`) and writes it temporarily to `Databases/Otp.txt`.
  2. [`Mail.java`](file:///e:/Library_Management_System_Terminal/src/Mail/Mail.java#L31) configures an SMTP session (`smtp.gmail.com:587`, TLS enabled) using `javax.mail` and dispatches an HTML email.
  3. When the user enters the OTP in terminal, `verifyOTP()` compares the input against `Otp.txt` and **immediately clears the file content** upon success to prevent replay attacks.

---

## 🔄 4. Detailed Step-by-Step Code Execution Path

```text
                     [1. Main.java] (App Starts)
                           │
                           ▼
             [2. IniciateLoginClass.start()]
                           │
           ┌───────────────┴───────────────┐
           ▼                               ▼
    Choose: 1) Admin                Choose: 2) Member
           │                               │
           ▼                               ▼
    [loadAdmins()]               Choose: 1) Faculty  OR  2) Student
  Reads Admins.csv                         │                 │
           │                               ▼                 ▼
           │                     [loadMembers(1)]    [loadMembers(2)]
           │                     Reads Faculty.csv   Reads Student.csv
           │                               │                 │
           │                               └────────┬────────┘
           │                                        │
           │                              [Check Blocked Users]
           │                             Reads BlockedUsers.csv
           │                                        │
           ▼                                        ▼
    [Admin Object]                         [Student/Faculty Object]
           │                                        │
           └───────────────────┬────────────────────┘
                               │
                               ▼
                    [3. LoginSystem.performLogin()]
                     Runs user.login() polymorphically
                     (If Admin: prompts 'secureAdmin123' token)
                               │
                               ▼
                     [4. Back to Main.java]
               Identifies role via getClass().getSimpleName()
                               │
           ┌───────────────────┼───────────────────┐
           ▼                   ▼                   ▼
    If "Student"          If "Faculty"          If "Admin"
           │                   │                   │
           ▼                   ▼                   ▼
    [UserMain.java]     [UserMain.java]      [AdminMain.java]
    studentAction()     facultyAction()      Admin Controller
```

### 🏁 STEP 1: Program Entry Point
* **File Called**: [`Main.java`](file:///e:/Library_Management_System_Terminal/src/Main.java#L16)
* **What happens**:
  * `Main.main()` runs when you start the app.
  * It opens the primary Scanner: `Scanner scanner = new Scanner(System.in)`.
  * It calls the login system: `boolean isSuccess = IniciateLoginClass.start()`.

### 🔐 STEP 2: Login Menu & Role Identification
* **File Called**: [`IniciateLoginClass.java`](file:///e:/Library_Management_System_Terminal/src/Login/IniciateLoginClass.java#L30)
* **What happens**:
  * Shows Terminal Menu: `1) Admin` or `2) Member`.

#### **PATH A: User selects `1) Admin`**
* Calls `loadAdmins()`: Reads `Databases/Admins.csv` using [`CSVReader.readCSV()`](file:///e:/Library_Management_System_Terminal/src/FileFunction/CSVReader.java#L106).
* Prompts User ID & Password.
* Finds matching `Admin` object in memory.
* Passes `Admin` to [`LoginSystem.performLogin(admin, userId, password)`](file:///e:/Library_Management_System_Terminal/src/Login/LoginSystem.java#L15).
* `LoginSystem` checks `if (user instanceof Admin)` -> Triggers 2FA token check via [`SecurityTokenCheck.handleAdminSecurityToken(1)`](file:///e:/Library_Management_System_Terminal/src/Login/SecurityTokenCheck.java#L18) (Prompts for security key `secureAdmin123`).
* If valid, sets `IniciateLoginClass.currentUser = admin` and returns `true`.

#### **PATH B: User selects `2) Member`**
* Shows Sub-Menu: `1) Faculty` or `2) Student`.
* Reads the appropriate file using [`CSVReader.readCSV()`](file:///e:/Library_Management_System_Terminal/src/FileFunction/CSVReader.java#L106):
  * Option 1 (Faculty) -> Reads `Databases/Faculty.csv` into a `List<Faculty>`.
  * Option 2 (Student) -> Reads `Databases/Student.csv` into a `List<Student>`.
* Prompts User ID.
* **Blocked Check**: Calls `isBlockedUser(userId)` -> Reads `Databases/BlockedUsers.csv`. If user ID exists, blocks login instantly!
* Prompts Password and finds matching `Student` or `Faculty` object.
* Wraps user into `LibraryMember logicUser = new LibraryMember(...)`.
* Calls `loginSystem.performLogin(logicUser, userId, password)`.
* Sets `IniciateLoginClass.currentMember = user` (stores actual `Student` or `Faculty` instance) and returns `true`.

### 🎯 STEP 3: Dynamic Role Routing
* **File Called**: [`Main.java`](file:///e:/Library_Management_System_Terminal/src/Main.java#L23-L40)
* **What happens**: `Main.java` checks the runtime class name of the logged-in user using Reflection:
  ```java
  String userType = IniciateLoginClass.currentMember != null
          ? IniciateLoginClass.currentMember.getClass().getSimpleName() // "Student" or "Faculty"
          : IniciateLoginClass.currentUser.getClass().getSimpleName();   // "Admin"
  ```
  It then routes to the specific controller via a `switch (userType)`:
  * Case `"Student"` ──► [`UserMain.studentAction()`](file:///e:/Library_Management_System_Terminal/src/Main/UserMain.java#L16)
  * Case `"Faculty"` ──► [`UserMain.facultyAction()`](file:///e:/Library_Management_System_Terminal/src/Main/UserMain.java#L60)
  * Case `"Admin"` ──► [`AdminMain.start()`](file:///e:/Library_Management_System_Terminal/src/Main/AdminMain.java#L25)

### ⚙️ STEP 4: Executing User Operations

#### 🧑‍🎓 If Logged in as Student:
* Calls [`UserMain.studentAction()`](file:///e:/Library_Management_System_Terminal/src/Main/UserMain.java#L16).
* Options:
  1. **Book Actions**: Calls `UserActionManager.UserActionManager()`. Checks limits via `UserActionValidator` (max 7 borrowed, 5 reserved, 5 cart). Writes entries to CSV via `UserActionService`.
  2. **Renew**: Calls `UserActionManager.Renewal()`. Updates dates in `BorrowedBooks.csv` via `BorrowedBooksService`.
  3. **Account Summary**: Calls `UserActionHandler.viewAccountSummary()`. Displays CLI formatted tables via `ViewAccountSummary`.
  4. **Give Feedback**: Calls `UserActionHandler.giveFeedback()`. Writes entry to `Feedback.csv`.

#### 👨‍🏫 If Logged in as Faculty:
* Calls [`UserMain.facultyAction()`](file:///e:/Library_Management_System_Terminal/src/Main/UserMain.java#L60).
* Same options as Student + **Option 5 (`Request Book`)**:
  * Prompts title/author & reason -> Writes entry to `RequestBooks.csv` via `BookRequest`.

#### 👑 If Logged in as Admin:
* Calls [`AdminMain.start()`](file:///e:/Library_Management_System_Terminal/src/Main/AdminMain.java#L25).
* Options:
  1. **User Action**: Add/Update/Delete users via `LibraryUserManager` & `ManageUsersDetails`; calculate overdue fines via `BorrowedBooksService.applyFines()`; view blocked users.
  2. **Book Action**: Add/Update/Delete books via `LibraryBookManager` & `ManageBooksDetails`; view system-wide lists via `LibraryAccountSummary`.
  3. **SendMail**: Sends batch emails via `UserNotificationService` and `Mail.java` (Renewal alerts, Return alerts, Overdue alerts, Blocked account alerts).
  4. **Others**: Review member feedback (`Feedback.displayFeedbacks()`) and faculty book requests (`BookRequest.displayRequestBooks()`).

### 🚪 STEP 5: Program Exit
* When the user selects `Exit` on any main menu:
  * Loop breaks ──► Returns to `Main.java` ──► Executes `scanner.close()` ──► Application terminates cleanly.

---

## 📂 5. Complete Package & File Structure Reference

```text
src/
├── Main.java                             # Entry point; routes role to UserMain or AdminMain
├── Main/
│   ├── UserMain.java                     # CLI Controller for Students & Faculty
│   └── AdminMain.java                    # CLI Controller for Admins
├── Users/
│   ├── LibraryUser.java                  # Base Data Model for all users
│   ├── Student.java                      # Subclass of LibraryUser (adds batch, year)
│   ├── Faculty.java                      # Subclass of LibraryUser (adds department)
│   ├── BlockedUser.java                  # Model & loader for blocked overdue users
│   ├── ManageUsersDetails.java           # Data Access Layer (DAO) for Student/Faculty CSV files
│   └── LibraryUserManager.java           # CLI Controller for Admin User Management (uses Composition)
├── Books/
│   ├── Book.java                         # Data Model for Books
│   ├── ManageBooksDetails.java           # Data Access Layer (DAO) for Books.csv
│   ├── LibraryBookManager.java           # CLI Controller for Admin Book Operations
│   ├── BookRequest.java                  # Model & display helper for Faculty book requests
│   └── Feedback.java                     # Model & display helper for User feedback
├── Login/
│   ├── User.java                         # Base Authentication Model
│   ├── Login.java                        # Interface declaring login() & logout()
│   ├── Admin.java                        # Extends User, implements Login
│   ├── LibraryMember.java                # Extends User, implements Login (covers Student & Faculty)
│   ├── LoginSystem.java                  # Polymorphic Login Engine
│   ├── IniciateLoginClass.java           # Workflow Coordinator (handles menus & file loading)
│   ├── SignUp.java                       # Member Self-Registration Controller
│   ├── OTPManager.java                   # Generates 6-digit OTP, writes to Otp.txt, wipes after verification
│   ├── OTPVerification.java              # Coordinates OTP generation & mail sending
│   ├── OTPHandler.java                   # CLI helper for OTP retries
│   └── SecurityTokenCheck.java           # Prompts for 'secureAdmin123' token
├── UserActions/
│   ├── BorrowedBooks.java                # Data Model for Borrowed Records
│   ├── BorrowedBooksService.java         # Fine calculation (₹10/₹25 overdue), returns, renewals
│   ├── Reservation.java                  # Data Model for Book Reservations (3-day pickup deadline)
│   ├── CartCollection.java               # Data Model for Shopping Cart Books
│   ├── UserActionValidator.java          # Limits Enforcer (Max 7 Borrowed, 5 Reserved, 5 Cart)
│   ├── UserActionService.java            # Record Creation Service for Borrow/Reserve/Cart
│   ├── UserActionManager.java            # CLI Coordinator for User Borrow/Reserve/Cart
│   ├── UserActionHandler.java            # Non-borrow action handler (Summary, Feedback, Requests)
│   ├── ViewAccountSummary.java           # CLI Table Viewer for individual user account summary
│   ├── LibraryAccountSummary.java        # System-wide summary viewer for Admin
│   └── LibraryDataCleaner.java           # Deletes expired reservations & old cart items
├── SearchQuerys/
│   ├── SearchBooks.java                  # Book Search Engine using Java Streams
│   ├── SearchUsers.java                  # User Search Engine using Bounded Generics (<T extends LibraryUser>)
│   ├── InitiateSearchBookFunctions.java  # CLI Menu for Book Searches
│   └── InitiateSearchUserFunctions.java  # CLI Menu for User Searches
├── Mail/
│   ├── Mail.java                         # SMTP Client Engine using javax.mail over TLS (port 587)
│   └── UserNotificationService.java      # Batch Email Alert Manager for renewal/return/overdue alerts
├── FileFunction/
│   ├── CSVReader.java                    # Generic CSV Reader using Reflection API & arraycopy
│   ├── CSVWriter.java                    # Generic CSV Writer using Reflection API
│   └── MalformedCSVException.java        # Custom Exception for invalid CSV lines
├── DateUtils/
│   └── DateUtils.java                    # Date calculations (+7 days renewal, +28 days return, +3 days pickup)
└── InputValidationUtils/
    └── ValidationUtils.java              # RegEx rules + @FunctionalInterface InputValidator<T>
```
