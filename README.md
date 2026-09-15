# Smart Library System

A simple **Java-based Library Management System** developed as a console application. It allows a library to manage books, members, and borrowing/returning of books.

The project demonstrates basic **Object-Oriented Programming (OOP)** concepts such as inheritance, polymorphism, encapsulation, abstraction, and exception handling.

## Features

* Add and manage books
* Register library members
* Support for different types of members

  * Student
  * Faculty
* Issue books to members
* Return issued books
* Check book availability
* Calculate fines for late returns
* Apply different borrowing rules for different members
* Save library data using CSV files
* Handle invalid operations using custom exceptions

## Technologies Used

* **Language:** Java
* **Programming Concepts:** OOP, Collections, Exception Handling, File Handling
* **Data Storage:** CSV files
* **IDE:** IntelliJ IDEA / Eclipse / VS Code (any Java-supported IDE)

## Project Structure

```text
smart_library_system/
│
├── src/
│   ├── Main.java
│   │
│   ├── model/
│   │   ├── Book.java
│   │   ├── Member.java
│   │   ├── StudentMember.java
│   │   ├── FacultyMember.java
│   │   └── Transaction.java
│   │
│   ├── service/
│   │   └── LibraryService.java
│   │
│   ├── exception/
│   │   ├── BookNotAvailableException.java
│   │   └── MemberLimitExceededException.java
│   │
│   └── util/
│       └── FileStorageManager.java
│
├── data/
│   └── CSV data files
│
└── README.md
```

## How the System Works

The system keeps information about books, members, and transactions.

### Books

Each book contains information such as its ID, title, author, and availability status.

A book can be issued only when it is available. After it is returned, it becomes available again.

### Members

The system supports different types of library members.

`Member` contains the common properties and operations shared by members. `StudentMember` and `FacultyMember` extend it and can have different borrowing limits and fine rules.

This is where **inheritance and polymorphism** are used in the project.

### Transactions

A transaction records the issue and return of a book. It can also be used to determine whether a fine needs to be charged.

## Exception Handling

The project uses custom exceptions for situations where an operation cannot be completed.

For example:

* `BookNotAvailableException` — used when someone tries to issue a book that is already issued.
* `MemberLimitExceededException` — used when a member has reached their borrowing limit.

This helps keep errors separate from the normal program flow.

## Data Storage

The project uses CSV files to store the library data.

This allows the data to remain available after the program is closed and started again. The required data files can be created/updated when the application saves its data.

## Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/sakshirathi7001/smart_library_system.git
```

### 2. Open the project

Open the project in a Java-supported IDE.

### 3. Compile the source files

Make sure Java is installed and configured correctly.

### 4. Run the program

Run:

```text
Main.java
```

The application can then be used through the console menu.

## OOP Concepts Used

The project was designed to practice the following OOP concepts:

| Concept            | Example                                                                  |
| ------------------ | ------------------------------------------------------------------------ |
| Encapsulation      | Classes keep their data and related methods together                     |
| Inheritance        | `StudentMember` and `FacultyMember` extend `Member`                      |
| Polymorphism       | Member-specific behavior can be handled through the parent `Member` type |
| Abstraction        | Common member functionality is separated from specific member types      |
| Exception Handling | Custom exceptions handle invalid library operations                      |

## Example Operations

The system can perform operations such as:

```text
1. Add Book
2. Add Member
3. Display Books
4. Display Members
5. Issue Book
6. Return Book
7. Check Book Availability
8. Exit
```

The exact options may vary depending on the current implementation.

## Future Improvements

Some features that could be added later include:

* Search and filter books
* Login system for librarians
* Database support using MySQL
* Graphical user interface
* Book reservation system
* More detailed transaction history
* Reports for issued and overdue books

## Author

**Sakshi Rathi**

GitHub: https://github.com/sakshirathi7001/smart_library_system
Project Report: report/SMART LIBRARY.docx

## License

This project was created as an academic/project exercise for learning Java and Object-Oriented Programming.
