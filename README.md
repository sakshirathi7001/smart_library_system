# Smart Library & Fine Management System

A console-based Java application for managing library books, members, and borrowing transactions, including automatic overdue fine calculation.

# Overview

This project simulates a library management system where a librarian can register members (students or faculty), maintain a book catalog, issue and return books, and track overdue fines. Data is persisted between runs using CSV files.

## Features

- **Member Management**: Register students and faculty members, each with different borrowing limits and durations
- **Book Catalog**: Add books, track total vs. available copies, search by title
- **Issue/Return System**: Issue books with due dates based on member type, return books with automatic overdue fine calculation
- **Fine Engine**: Different fine rates for students ($2/day) and faculty ($1/day) via polymorphism
- **Persistent Storage**: All data saved to CSV files in the `data/` folder and reloaded on startup
- **Custom Exception Handling**: Prevents issuing unavailable books or exceeding a member's borrowing limit

## Technologies Used

- Java (JDK 17+)
- Java Collections (HashMap, ArrayList)
- java.time (LocalDate) for date handling
- File I/O for CSV-based persistence

## Project Structure
LibrarySystem/
├── src/
│ ├── model/ # Book, Member, StudentMember, FacultyMember, Transaction
│ ├── exception/ # Custom exceptions
│ ├── service/ # Core business logic (LibraryService)
│ ├── util/ # FileStorageManager for CSV persistence
│ └── Main.java # CLI entry point
├── data/ # Auto-generated CSV data files (created on first run)
└── README.md

## Prerequisites

- Java Development Kit (JDK) 17 or later installed
- Verify with: `java -version` and `javac -version`

## How to Set Up and Run

1. Clone the repository:git clone https://github.com/sakshirathi7001/smart_library_system.git
cd {smart_library_system}

2. Compile the project:

   **Mac/Linux:**javac -d out $(find src -name "*.java")
   
   **Windows (PowerShell):**javac -d out (Get-ChildItem -Recurse -Filter *.java src).FullName

3. Run the application:java -cp out Main

4. On first run, sample data (2 members, 2 books) loads automatically. Use the on-screen menu to add members, add books, issue/return books, and search the catalog.

5. On exit (option `0`), all data is saved to the `data/` folder and will reload automatically on the next run.

## Testing Instructions

Manually test the following flows:
- Add a new student and faculty member (option 1) and verify different borrowing limits apply
- Add a book with multiple copies (option 2), issue it to a member (option 3), and confirm available copies decrease
- Attempt to issue a book with 0 available copies — should show a `BookNotAvailableException` message
- Return a book late (option 4) and confirm the fine is calculated correctly based on member type
- Exit and re-run the program — confirm previously added data persists

## Future Enhancements

- Migrate from CSV storage to a relational database (e.g., SQLite/MySQL)
- Add email/SMS notifications for overdue books
- Add a REST API layer for web-based access