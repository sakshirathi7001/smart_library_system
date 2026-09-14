package model;

public class StudentMember extends Member {

    public StudentMember(String memberId, String name) {
        super(memberId, name);
        this.maxBooksAllowed = 3;
        this.borrowDurationDays = 14;
    }

    @Override
    public double calculateFine(int daysLate) {
        if (daysLate <= 0) {
            return 0.0;
        }
        double finePerDay = 2.0;
        return daysLate * finePerDay;
    }
}