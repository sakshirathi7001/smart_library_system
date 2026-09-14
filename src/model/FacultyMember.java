package model;

public class FacultyMember extends Member {

    public FacultyMember(String memberId, String name) {
        super(memberId, name);
        this.maxBooksAllowed = 8;
        this.borrowDurationDays = 30;
    }

    @Override
    public double calculateFine(int daysLate) {
        if (daysLate <= 0) {
            return 0.0;
        }
        double finePerDay = 1.0;
        return daysLate * finePerDay;
    }
} 