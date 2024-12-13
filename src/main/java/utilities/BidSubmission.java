package utilities;

public class BidSubmission {
    private String fullName;
    private String email;
    private String proposal;
    private String date;

    public BidSubmission(String fullName, String email, String proposal, String date) {
        this.fullName = fullName;
        this.email = email;
        this.proposal = proposal;
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getProposal() {
        return proposal;
    }

    public String getDate() {
        return date;
    }
}
