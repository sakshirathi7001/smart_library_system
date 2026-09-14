package model;

public abstract class Member {
    protected String memberId;
    protected String name;
    protected int maxBooksAllowed;
    protected int borrowDurationDays;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public abstract double calculateFine(int daysLate);

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public int getMaxBooksAllowed() { return maxBooksAllowed; }
    public int getBorrowDurationDays() { return borrowDurationDays; }

    @Override
    public String toString() {
        return memberId + " - " + name;
    }
}