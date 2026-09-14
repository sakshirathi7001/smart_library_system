package util;

import model.Book;
import model.Member;
import model.StudentMember;
import model.FacultyMember;
import model.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileStorageManager {

    private static final String BOOKS_FILE = "data/books.csv";
    private static final String MEMBERS_FILE = "data/members.csv";
    private static final String TRANSACTIONS_FILE = "data/transactions.csv";

    public void saveBooks(Collection<Book> books) {
        try {
            new File("data").mkdirs();
            PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE));
            for (Book b : books) {
                writer.println(b.getIsbn() + "," + b.getTitle() + "," + b.getAuthor()
                        + "," + b.getTotalCopies() + "," + b.getAvailableCopies());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);
        if (!file.exists()) return books;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Book book = new Book(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
                int available = Integer.parseInt(parts[4]);
                int borrowed = book.getTotalCopies() - available;
                for (int i = 0; i < borrowed; i++) {
                    book.borrowCopy();
                }
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
        return books;
    }

    public void saveMembers(Collection<Member> members) {
        try {
            new File("data").mkdirs();
            PrintWriter writer = new PrintWriter(new FileWriter(MEMBERS_FILE));
            for (Member m : members) {
                String type = (m instanceof FacultyMember) ? "FACULTY" : "STUDENT";
                writer.println(m.getMemberId() + "," + m.getName() + "," + type);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving members: " + e.getMessage());
        }
    }

    public List<Member> loadMembers() {
        List<Member> members = new ArrayList<>();
        File file = new File(MEMBERS_FILE);
        if (!file.exists()) return members;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String name = parts[1];
                String type = parts[2];

                Member member = type.equals("FACULTY")
                        ? new FacultyMember(id, name)
                        : new StudentMember(id, name);
                members.add(member);
            }
        } catch (IOException e) {
            System.out.println("Error loading members: " + e.getMessage());
        }
        return members;
    }

    public void saveTransactions(Collection<Transaction> transactions) {
        try {
            new File("data").mkdirs();
            PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTIONS_FILE));
            for (Transaction t : transactions) {
                String returnDateStr = (t.getReturnDate() == null) ? "NULL" : t.getReturnDate().toString();
                writer.println(t.getTransactionId() + "," + t.getMemberId() + "," + t.getIsbn()
                        + "," + t.getIssueDate() + "," + t.getDueDate() + "," + returnDateStr);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        if (!file.exists()) return transactions;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Transaction t = new Transaction(parts[0], parts[1], parts[2],
                        LocalDate.parse(parts[3]), LocalDate.parse(parts[4]));
                if (!parts[5].equals("NULL")) {
                    t.markReturned(LocalDate.parse(parts[5]));
                }
                transactions.add(t);
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }
}