package service;

import model.Book;
import model.Member;
import model.Transaction;
import exception.BookNotAvailableException;
import exception.MemberLimitExceededException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.FileStorageManager;
public class LibraryService {

    private Map<String, Book> books;
    private Map<String, Member> members;
    private List<Transaction> transactions;
    private int transactionCounter;
    private FileStorageManager storageManager;

    public LibraryService() {
      books = new HashMap<>();
      members = new HashMap<>();
      transactions = new ArrayList<>();
      transactionCounter = 1;
      storageManager = new FileStorageManager();
      loadAllData();
}

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public void addMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    public Book findBookByIsbn(String isbn) {
        return books.get(isbn);
    }

    public List<Book> searchByTitle(String keyword) {
        List<Book> results = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(b);
            }
        }
        return results;
    }

    private int countActiveLoans(String memberId) {
        int count = 0;
        for (Transaction t : transactions) {
            if (t.getMemberId().equals(memberId) && !t.isReturned()) {
                count++;
            }
        }
        return count;
    }

    public Transaction issueBook(String memberId, String isbn)
            throws BookNotAvailableException, MemberLimitExceededException {

        Member member = members.get(memberId);
        Book book = books.get(isbn);

        if (book == null || !book.isAvailable()) {
            throw new BookNotAvailableException("Book with ISBN " + isbn + " is not available.");
        }

        int activeLoans = countActiveLoans(memberId);
        if (activeLoans >= member.getMaxBooksAllowed()) {
            throw new MemberLimitExceededException(
                    member.getName() + " has reached their borrowing limit of " + member.getMaxBooksAllowed());
        }

        book.borrowCopy();
        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(member.getBorrowDurationDays());

        String transactionId = "T" + transactionCounter++;
        Transaction transaction = new Transaction(transactionId, memberId, isbn, issueDate, dueDate);
        transactions.add(transaction);

        return transaction;
    }

    public double returnBook(String transactionId) {
        for (Transaction t : transactions) {
            if (t.getTransactionId().equals(transactionId) && !t.isReturned()) {
                LocalDate today = LocalDate.now();
                t.markReturned(today);

                Book book = books.get(t.getIsbn());
                if (book != null) {
                    book.returnCopy();
                }

                Member member = members.get(t.getMemberId());
                long daysLate = t.getDaysLate(today);
                return member.calculateFine((int) daysLate);
            }
        }
        return -1;
    }

    public List<Transaction> getOverdueTransactions() {
        List<Transaction> overdue = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Transaction t : transactions) {
            if (!t.isReturned() && today.isAfter(t.getDueDate())) {
                overdue.add(t);
            }
        }
        return overdue;
    }
    private void loadAllData() {
    for (model.Book b : storageManager.loadBooks()) {
        books.put(b.getIsbn(), b);
    }
    for (model.Member m : storageManager.loadMembers()) {
        members.put(m.getMemberId(), m);
    }
    List<model.Transaction> loadedTransactions = storageManager.loadTransactions();
    transactions.addAll(loadedTransactions);

    for (model.Transaction t : loadedTransactions) {
        try {
            int num = Integer.parseInt(t.getTransactionId().substring(1));
            if (num >= transactionCounter) {
                transactionCounter = num + 1;
            }
        } catch (NumberFormatException e) {
            // ignore malformed IDs
        }
    }
 }

    public void saveAllData() {
      storageManager.saveBooks(books.values());
      storageManager.saveMembers(members.values());
      storageManager.saveTransactions(transactions);
 }

    public Map<String, Book> getAllBooks() { return books; }
    public Map<String, Member> getAllMembers() { return members; }
    public List<Transaction> getAllTransactions() { return transactions; }
 }