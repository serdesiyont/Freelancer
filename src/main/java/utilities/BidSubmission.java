package utilities;


import java.time.LocalDate;

public class BidSubmission {
    private String email;
    private String experience;
    private String date;
    private int price;
    private String jobTitle;
    private final String status;

    public BidSubmission(String email, String experience, LocalDate date, int price, String status, String jobTitle) {
        this.email = email;
        this.experience = experience;
        this.date = date.toString();
        this.price = price;
        this.status = status;
        this.jobTitle = jobTitle;
    }


    public String getEmail() {
        return email;
    }

    public String getExperience() {
        return experience;
    }

    public String getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getJobTitle() {
        return jobTitle;
    }
}
