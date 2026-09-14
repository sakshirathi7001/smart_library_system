# Project Statement

## Problem Statement

Libraries often rely on manual or spreadsheet-based tracking for managing books, member borrowing limits, and overdue fines. This leads to inconsistent fine calculation, difficulty tracking which books are available, and no clear enforcement of borrowing limits across different types of members (students vs. faculty). This project addresses that gap with a lightweight, rule-based library management system.

## Scope

This project covers:
- Managing library members with role-based borrowing rules (students vs. faculty)
- Maintaining a book catalog with copy-level availability tracking
- Issuing and returning books with automatic due-date assignment
- Calculating overdue fines based on member type
- Persisting all data across sessions using file-based storage

It does not cover: multi-branch library networks, online reservations, or payment gateway integration for fines — these are noted as future enhancements.

## Target Users

- **Librarians / Library Staff**: Primary users who operate the system to manage the catalog, members, and transactions
- **Academic institutions**: Small to mid-sized library setups (e.g., a department library) that need a simple, dependency-free system without setting up a full database server

## High-Level Features

1. Member registration with two role types (Student, Faculty), each with distinct borrowing limits and durations
2. Book catalog management with multi-copy tracking
3. Book issue workflow with limit and availability validation (via custom exceptions)
4. Book return workflow with automatic fine calculation based on days overdue and member type
5. Search functionality to find books by title keyword
6. Overdue transaction reporting
7. Persistent CSV-based storage so data survives application restarts