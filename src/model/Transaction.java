package model;

import java.time.LocalDate;

public class Transaction {
    private String transactionId;
    private String memberId;
    private String isbn;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Transaction(String transactionId, String memberId, String isbn,
                        LocalDate issueDate, LocalDate dueDate) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.isbn = isbn;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void markReturned(LocalDate date) {
        this.returnDate = date;
    }

    public long getDaysLate(LocalDate onDate) {
        if (onDate.isAfter(dueDate)) {
            return java.time.temporal.ChronoUnit.DAYS.between(dueDate, onDate);
        }
        return 0;
    }

    public String getTransactionId() { return transactionId; }
    public String getMemberId() { return memberId; }
    public String getIsbn() { return isbn; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    @Override
    public String toString() {
        String status = isReturned() ? "Returned on " + returnDate : "Not returned (due " + dueDate + ")";
        return transactionId + " | Member: " + memberId + " | Book: " + isbn + " | " + status;
    }
}