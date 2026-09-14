# 📚 Smart Library & Fine Management System

A console-based **Java** application for managing library books, members, and borrowing transactions — including automatic overdue fine calculation and CSV-based persistence.

![Language](https://img.shields.io/badge/Language-Java%2017%2B-orange)
![Type](https://img.shields.io/badge/Type-Console%20Application-blue)
![Storage](https://img.shields.io/badge/Storage-CSV%20File--based-green)
![Status](https://img.shields.io/badge/Status-Academic%20Project-lightgrey)

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [System Architecture](#system-architecture)
- [UML Diagrams](#uml-diagrams)
  - [Class Diagram](#1-class-diagram)
  - [Use Case Diagram](#2-use-case-diagram)
  - [Sequence Diagram](#3-sequence-diagram--issue-book)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [How to Set Up and Run](#how-to-set-up-and-run)
- [Usage / Menu Guide](#usage--menu-guide)
- [Data Persistence](#data-persistence)
- [Testing Instructions](#testing-instructions)
- [Future Enhancements](#future-enhancements)
- [Project Report](#project-report)

---

## Overview

This project simulates a library management system where a librarian can register members (students or faculty), maintain a book catalog, issue and return books, and track overdue fines. Data is persisted between runs using CSV files, so nothing is lost when the application restarts.

The system enforces different rules for **students** and **faculty**:

| Member Type | Max Books Allowed | Borrow Duration | Fine Rate (per day late) |
|---|---|---|---|
| Student | 3 | 14 days | $2.00 |
| Faculty | 8 | 30 days | $1.00 |

## Features

- **Member Management** — Register students and faculty members, each with different borrowing limits and durations
- **Book Catalog** — Add books, track total vs. available copies, search by title
- **Issue/Return System** — Issue books with due dates based on member type, return books with automatic overdue fine calculation
- **Fine Engine** — Different fine rates for students and faculty via polymorphism (`Member.calculateFine()`)
- **Persistent Storage** — All data saved to CSV files in `data/` and reloaded automatically on startup
- **Custom Exception Handling** — Prevents issuing unavailable books or exceeding a member's borrowing limit via `BookNotAvailableException` and `MemberLimitExceededException`
- **Overdue Reporting** — Lists every transaction that is currently past its due date

## Technologies Used

- Java (JDK 17+)
- Java Collections (`HashMap`, `ArrayList`)
- `java.time.LocalDate` for date handling
- File I/O for CSV-based persistence
- Object-Oriented Design — abstraction, inheritance, polymorphism, and custom exceptions

## System Architecture

The application follows a simple layered structure:


Main (CLI)  →  LibraryService (business logic)  →  FileStorageManager (persistence)
                        ↓
              model classes (Book, Member, Transaction)


- **`Main`** — the console entry point; renders the menu and reads user input
- **`service.LibraryService`** — the single source of truth for books, members, and transactions; owns all business rules (availability checks, borrowing limits, fine calculation dispatch)
- **`util.FileStorageManager`** — reads and writes the three CSV files under `data/`
- **`model`** — plain data/domain classes: `Book`, `Member` (abstract), `StudentMember`, `FacultyMember`, `Transaction`
- **`exception`** — checked exceptions `BookNotAvailableException` and `MemberLimitExceededException`, thrown by `LibraryService.issueBook()` and handled in `Main`

## UML Diagrams

### 1. Class Diagram

Shows every domain class, its attributes/methods, and the relationships between them (inheritance, composition, and thrown-exception dependencies).

![Class Diagram](docs/diagrams/class_diagram.png)

### 2. Use Case Diagram

Shows the Librarian actor and every operation the system supports, including `«include»`/`«extend»` relationships for validation and persistence steps.

![Use Case Diagram](docs/diagrams/usecase_diagram.png)

### 3. Sequence Diagram — Issue Book

Traces the full **Issue Book** flow end to end, including the `alt` fragment covering the exception path when a book is unavailable or a member has hit their borrowing limit.

![Sequence Diagram](docs/diagrams/sequence_diagram.png)

## Project Structure

smart_library_system/
├── src/
│   ├── Main.java                          # CLI entry point
│   ├── model/
│   │   ├── Book.java
│   │   ├── Member.java                    # abstract base class
│   │   ├── StudentMember.java
│   │   ├── FacultyMember.java
│   │   └── Transaction.java
│   ├── exception/
│   │   ├── BookNotAvailableException.java
│   │   └── MemberLimitExceededException.java
│   ├── service/
│   │   └── LibraryService.java            # core business logic
│   └── util/
│       └── FileStorageManager.java        # CSV persistence
├── data/                                  # auto-generated CSV data files
│   ├── books.csv
│   ├── members.csv
│   └── transactions.csv
├── docs/
│   └── diagrams/                          # class / use case / sequence diagrams
├── statement.md                           # original problem statement
└── README.md

## Prerequisites

- Java Development Kit (JDK) 17 or later
- Verify with:
  ```bash
  java -version
  javac -version
  ```

## How to Set Up and Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/sakshirathi7001/smart_library_system.git
   cd smart_library_system
   ```

2. **Compile the project**

   Mac/Linux:
   ```bash
   javac -d out $(find src -name "*.java")
   ```

   Windows (PowerShell):
   ```powershell
   javac -d out (Get-ChildItem -Recurse -Filter *.java src).FullName
   ```

3. **Run the application**
   ```bash
   java -cp out Main
   ```

4. On first run (when there is no existing data), sample data (2 members, 2 books) loads automatically. Use the on-screen menu to add members, add books, issue/return books, and search the catalog.

5. On exit (option `0`), all data is saved to the `data/` folder and reloads automatically on the next run.

## Usage / Menu Guide


===== SMART LIBRARY SYSTEM =====
1. Add Member
2. Add Book
3. Issue Book
4. Return Book
5. Search Book by Title
6. View Overdue Transactions
7. List All Books
8. List All Members
0. Exit


| Option | Action |
|---|---|
| 1 | Register a new member as a Student or Faculty |
| 2 | Add a new book (or additional copies) to the catalog |
| 3 | Issue a book to a member — validates availability and borrowing limit |
| 4 | Return a book by transaction ID — calculates any overdue fine |
| 5 | Search the catalog by a title keyword (case-insensitive) |
| 6 | List all transactions that are currently overdue |
| 7 | Print the full book catalog with available/total copies |
| 8 | Print all registered members |
| 0 | Save all data to CSV and exit |

## Data Persistence

All state is stored as plain CSV under `data/`, created automatically on first save:

| File | Columns |
|---|---|
| `books.csv` | isbn, title, author, totalCopies, availableCopies |
| `members.csv` | memberId, name, type (`STUDENT` / `FACULTY`) |
| `transactions.csv` | transactionId, memberId, isbn, issueDate, dueDate, returnDate (`NULL` if not yet returned) |

Data is loaded back into memory when `LibraryService` is constructed, so the application is fully stateful across runs without any external database.

## Testing Instructions

Manually verify the following flows:

- Add a new student and a new faculty member (option 1) and confirm different borrowing limits/durations apply
- Add a book with multiple copies (option 2), issue it to a member (option 3), and confirm available copies decrease
- Attempt to issue a book with 0 available copies — should raise a `BookNotAvailableException` message
- Attempt to issue more books than a member's limit allows — should raise a `MemberLimitExceededException` message
- Return a book late (option 4) and confirm the fine is calculated correctly based on member type
- Exit (option 0) and re-run the program — confirm all previously added data persists

## Future Enhancements

- Migrate from CSV storage to a relational database (e.g., SQLite/MySQL)
- Add email/SMS notifications for overdue books
- Add a REST API layer for web-based access
- Add unit tests (JUnit) covering `LibraryService` and fine-calculation logic
- Add reservation/holds support for books that are currently unavailable

## Project Report

A full 15-section project report — covering the problem statement, requirements, design, implementation details, testing, and conclusion — is available at [`docs/Smart_Library_System_Project_Report.pdf`](docs/Smart_Library_System_Project_Report.pdf).

---

## About

Java console app for library management — member tracking, book issue/return, and automatic overdue fine calculation.
