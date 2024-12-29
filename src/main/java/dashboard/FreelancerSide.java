package dashboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.Main;
import utilities.BidSubmission;
import utilities.JobPosting;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class FreelancerSide {
    private JobPosting selectedJob;
    String path = "src/main/java/utilities/jobpost.json";
    String pathBid = "src/main/java/utilities/bids.json";
    Scanner input = new Scanner(System.in);

    public void jobList() {
        try {
            System.out.println("=== Here are the Job lists: ===");
            Gson gson = new Gson();
            String json = new String(Files.readAllBytes(Paths.get(path)));

            Type listType = new TypeToken<List<JobPosting>>() {}.getType();
            List<JobPosting> jobPostings = gson.fromJson(json, listType);

            int count = 1;
            for (JobPosting job : jobPostings) {
                System.out.println(count + ". " + job.getTitle());
                System.out.println("-------------" + job.getCompany());
                System.out.println("-------------" + job.getSalary());
                System.out.println("-------------" + job.getType());
                count++;
            }

        } catch (IOException e) {
            System.out.println("Error reading the JSON file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Encountered an error: " + e.getMessage());
        }
    }

    public void getJob() {
        System.out.println("#######################");
        System.out.print("For more detail select the desired choice: ");
        int index = input.nextInt();
        try {
            Gson gson = new Gson();
            String json = new String(Files.readAllBytes(Paths.get(path)));

            Type listType = new TypeToken<List<JobPosting>>() {}.getType();
            List<JobPosting> jobList = gson.fromJson(json, listType);

            if (index >= 1 && index - 1 < jobList.size()) {
                JobPosting job = jobList.get(index - 1);
                selectedJob = jobList.get(index - 1);

                System.out.println("Title: " + job.getTitle());
                System.out.println("Company: " + job.getCompany());
                System.out.println("Type: " + job.getType());
                System.out.println("Salary: " + job.getSalary());
                System.out.println("Description: " + job.getDescription());
                System.out.println("Requirements: " + job.getRequirements());
            } else {
                System.out.println("You have chosen an invalid job, Please try again :(.");
            }

        } catch (Exception e) {
            System.out.println("Error reading the JSON file: " + e.getMessage());
        }

    }

    public void submitBid() {
        Scanner response = new Scanner(System.in);

        Main mainClass = Main.getInstance();
        String email = mainClass.getEmail();
        LocalDate date = LocalDate.now();

        System.out.println("=== To submit your proposal answer the following questions: ===");
        System.out.println("Description about your past experience that can be relatable to the current job application: ");
        String experience = response.nextLine();
        System.out.print("Enter the price to complete the project: ");
        int price = response.nextInt();

        String getJobTitle = selectedJob.getTitle();
        BidSubmission bid = new BidSubmission(email, experience, date, price, "Pending", getJobTitle);
        saveBidToJson(bid);
    }

    public void saveBidToJson(BidSubmission bid) {
        Gson gson = new Gson();

        List<BidSubmission> bids = readBidsFromFile();
        bids.add(bid);

        try (FileWriter writer = new FileWriter(pathBid)) {
            gson.toJson(bids, writer);
            System.out.println("*******************************");
            System.out.println("* Bid has been successfully submitted. *");
            System.out.println("*******************************");
        } catch (IOException e) {
            System.out.println("Error writing to the JSON file: " + e.getMessage());
        }
    }

    private List<BidSubmission> readBidsFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(pathBid)) {
            Type listType = new TypeToken<List<BidSubmission>>() {}.getType();

            List<BidSubmission> bids = gson.fromJson(reader, listType);

            return bids != null ? bids : new java.util.ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error reading from the JSON file: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public void getSubmittedBids() {
        Main mainClass = Main.getInstance();
        String email = mainClass.getEmail();

        List<BidSubmission> bids = readBidsFromFile();
        System.out.println("#######################");
        System.out.println("=== Your submitted bids: ===");
        int count = 1;
        for (BidSubmission bid : bids) {
            if (bid.getEmail().equals(email)) {
                System.out.println(count + ". Job Title: " + bid.getJobTitle());
                System.out.println("   " + "Experience: " + bid.getExperience());
                System.out.println("   " + "Date: " + bid.getDate());
                System.out.println("   " + "Price: " + bid.getPrice());
                System.out.println("   " + "Status: " + bid.getStatus());
                System.out.println("-----------------------------");
                count++;
            }
        }
    }

    public static void freelancer(String[] args) {
        System.out.println("#######################");
        FreelancerSide freelancer = new FreelancerSide();

        System.out.println("=== Welcome to your dashboard :) ===");
        Scanner input = new Scanner(System.in);

        System.out.println("1. JobList");
        System.out.println("2. Get submitted bids");
        System.out.print("Choose where to redirect: ");

        int response = input.nextInt();
        if (response == 1) {
            freelancer.jobList();
            freelancer.getJob();

            Scanner confirmation = new Scanner(System.in);
            System.out.print("To submit a bid, type 'submit': ");
            String submit = confirmation.next();

            if (submit.equalsIgnoreCase("submit")) {
                freelancer.submitBid();
                freelancer(args);
            }
        } else if (response == 2) {
            freelancer.getSubmittedBids();
            System.out.print("Do you want to go back to the dashboard? (yes/no): ");
            String goBack = input.next();
            if (goBack.equalsIgnoreCase("yes")) {
                freelancer(args);
            }
        } else {
            System.out.println("You have chosen an invalid option, please try again :(.");
        }
    }
}