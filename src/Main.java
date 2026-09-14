import model.Book;
import model.Member;
import model.StudentMember;
import model.FacultyMember;
import model.Transaction;
import service.LibraryService;
import exception.BookNotAvailableException;
import exception.MemberLimitExceededException;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static LibraryService library = new LibraryService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
    if (library.getAllBooks().isEmpty() && library.getAllMembers().isEmpty()) {
        seedSampleData();
    }

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addMember(); break;
                case "2": addBook(); break;
                case "3": issueBook(); break;
                case "4": returnBook(); break;
                case "5": searchBooks(); break;
                case "6": viewOverdue(); break;
                case "7": listAllBooks(); break;
                case "8": listAllMembers(); break;
                case "0":library.saveAllData();
                    running = false;
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== SMART LIBRARY SYSTEM =====");
        System.out.println("1. Add Member");
        System.out.println("2. Add Book");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. Search Book by Title");
        System.out.println("6. View Overdue Transactions");
        System.out.println("7. List All Books");
        System.out.println("8. List All Members");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addMember() {
        System.out.print("Member ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Type (1=Student, 2=Faculty): ");
        String type = scanner.nextLine().trim();

        Member member;
        if (type.equals("2")) {
            member = new FacultyMember(id, name);
        } else {
            member = new StudentMember(id, name);
        }
        library.addMember(member);
        System.out.println("Member added: " + member);
    }

    private static void addBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Author: ");
        String author = scanner.nextLine().trim();
        System.out.print("Number of copies: ");
        int copies = Integer.parseInt(scanner.nextLine().trim());

        Book book = new Book(isbn, title, author, copies);
        library.addBook(book);
        System.out.println("Book added: " + book);
    }

    private static void issueBook() {
        System.out.print("Member ID: ");
        String memberId = scanner.nextLine().trim();
        System.out.print("Book ISBN: ");
        String isbn = scanner.nextLine().trim();

        try {
            Transaction t = library.issueBook(memberId, isbn);
            System.out.println("Book issued successfully: " + t);
        } catch (BookNotAvailableException | MemberLimitExceededException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void returnBook() {
        System.out.print("Transaction ID: ");
        String tid = scanner.nextLine().trim();

        double fine = library.returnBook(tid);
        if (fine < 0) {
            System.out.println("Transaction not found or already returned.");
        } else if (fine == 0) {
            System.out.println("Book returned on time. No fine.");
        } else {
            System.out.println("Book returned late. Fine: $" + fine);
        }
    }

    private static void searchBooks() {
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine().trim();
        List<Book> results = library.searchByTitle(keyword);

        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            for (Book b : results) {
                System.out.println(b);
            }
        }
    }

    private static void viewOverdue() {
        List<Transaction> overdue = library.getOverdueTransactions();
        if (overdue.isEmpty()) {
            System.out.println("No overdue transactions.");
        } else {
            for (Transaction t : overdue) {
                System.out.println(t);
            }
        }
    }

    private static void listAllBooks() {
        for (Book b : library.getAllBooks().values()) {
            System.out.println(b);
        }
    }

    private static void listAllMembers() {
        for (Member m : library.getAllMembers().values()) {
            System.out.println(m);
        }
    }

    private static void seedSampleData() {
        library.addMember(new StudentMember("S1", "Aditi Sharma"));
        library.addMember(new FacultyMember("F1", "Dr. Rao"));
        library.addBook(new Book("ISBN001", "Effective Java", "Joshua Bloch", 2));
        library.addBook(new Book("ISBN002", "Clean Code", "Robert Martin", 1));
    }
}