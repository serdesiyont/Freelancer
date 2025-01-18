package utilities;


import java.time.LocalDate;

public class BidSubmission {
    private int id;
    private String email;
    private String experience;
    private String date;
    private int price;
    private String jobTitle;
    private String status;
    private String clientAddress;

    public BidSubmission(int id, String email, String experience, LocalDate date, int price, String status, String jobTitle) {
        this.id = id;
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
    public int getId(){
        return id;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
}
