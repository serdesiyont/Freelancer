package utilities;


import java.time.LocalDate;

public class BidSubmission {
    private String email;
    private String experience;
    private String date;
    private int price;

    public BidSubmission(String email, String experience, LocalDate date, int price) {
        this.email = email;
        this.experience = experience;
        this.date =  date.toString();
        this.price = price;
    }


    public String getEmail() {
        return email;
    }

    public String getExperience() {
        return experience;
    }

    public void getDate() {
        this.date = date.toString();
    }

    public int getPrice() {
        return price;
    }
}
