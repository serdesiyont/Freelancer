package dashboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.Objects;
import java.util.Scanner;

public class FreelancerSide {
    private JobPosting selectedJob;
    String path = "src/main/java/utilities/jobpost.json";
    String pathBid = "src/main/java/utilities/bids.json";
    Scanner input = new Scanner(System.in);

    public void jobList() {
        try {
            System.out.println("💼 === Available Job Listings === 💼");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = new String(Files.readAllBytes(Paths.get(path)));

            Type listType = new TypeToken<List<JobPosting>>() {}.getType();
            List<JobPosting> jobPostings = gson.fromJson(json, listType);

            int count = 1;
            for (JobPosting job : jobPostings) {
                System.out.printf("[%d] %s%n", count, job.getTitle());
                System.out.println("   🔹 Company: " + job.getCompany());
                System.out.println("   🔹 Salary: $" + job.getSalary());
                System.out.println("   🔹 Type: " + job.getType());
                System.out.println("-------------------------------------------------");
                count++;
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error: Unable to load job listings. (" + e.getMessage() + ")");
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected error: " + e.getMessage());
        }
    }
    int id;

    public void getJob() {
        System.out.println("📋 ========================= 📋");
        System.out.print("🔍 Enter the job number to view details: ");
        int index = input.nextInt();
        id = index;

        try {
            Gson gson = new Gson();
            String json = new String(Files.readAllBytes(Paths.get(path)));

            Type listType = new TypeToken<List<JobPosting>>() {}.getType();
            List<JobPosting> jobList = gson.fromJson(json, listType);

            if (index >= 1 && index - 1 < jobList.size()) {
                JobPosting job = jobList.get(index - 1);
                selectedJob = jobList.get(index - 1);

                System.out.println("\n📌 Job Details:");
                System.out.println("   🔹 Title: " + job.getTitle());
                System.out.println("   🔹 Company: " + job.getCompany());
                System.out.println("   🔹 Type: " + job.getType());
                System.out.println("   🔹 Salary: $" + job.getSalary());
                System.out.println("   🔹 Description: " + job.getDescription());
                System.out.println("   🔹 Requirements: " + job.getRequirements());
            } else {
                System.out.println("⚠️ Invalid selection. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error: Unable to load job details. (" + e.getMessage() + ")");
        }
    }

    public void submitBid() {
        Scanner response = new Scanner(System.in);

        Main mainClass = Main.getInstance();
        String email = mainClass.getEmail();
        LocalDate date = LocalDate.now();

        System.out.println("📝 === Submit Your Proposal === 📝");
        System.out.print("🔹 Describe your relevant experience: ");
        String experience = response.nextLine();

        int price = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("🔹 Enter your proposed price: $");
            if (response.hasNextInt()) {
                price = response.nextInt();
                validInput = true;
            } else {
                System.out.println("❌ Invalid input. Please enter a valid number.");
                response.next(); // Clear the invalid input
            }
        }

        String getJobTitle = selectedJob.getTitle();
        BidSubmission bid = new BidSubmission(id, email, experience, date, price, "Pending", getJobTitle);
        saveBidToJson(bid);
    }



    public void saveBidToJson(BidSubmission bid) {
        Gson gson = new Gson();

        List<BidSubmission> bids = readBidsFromFile();
        bids.add(bid);

        try (FileWriter writer = new FileWriter(pathBid)) {
            gson.toJson(bids, writer);
            System.out.println("\n✅ Proposal Submitted Successfully!");
            System.out.println("💬 Your bid is now under review.");
        } catch (IOException e) {
            System.out.println("⚠️ Error: Unable to submit the proposal. (" + e.getMessage() + ")");
        }
    }

    private List<BidSubmission> readBidsFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(pathBid)) {
            Type listType = new TypeToken<List<BidSubmission>>() {}.getType();
            List<BidSubmission> bids = gson.fromJson(reader, listType);
            return bids != null ? bids : new java.util.ArrayList<>();
        } catch (IOException e) {
            System.out.println("⚠️ Error: Unable to load previous bids. (" + e.getMessage() + ")");
            return new java.util.ArrayList<>();
        }
    }

    public void getSubmittedBids() {
        Main mainClass = Main.getInstance();
        String email = mainClass.getEmail();

        List<BidSubmission> bids = readBidsFromFile();
        System.out.println("📤 === Your Submitted Bids === 📤");
        int count = 1;
        for (BidSubmission bid : bids) {
            if (bid.getEmail().equals(email)) {
                System.out.printf("[%d] %s%n", count, bid.getJobTitle());
                System.out.println("   🔹 Experience: " + bid.getExperience());
                System.out.println("   🔹 Submitted On: " + bid.getDate());
                System.out.println("   🔹 Proposed Price: $" + bid.getPrice());
                System.out.println("   🔹 Status: " + bid.getStatus());
                if (!Objects.equals(bid.getClientAddress(), "")) {
                    System.out.println("   🔹 Client Address: " + bid.getClientAddress());
                }
                System.out.println("-------------------------------------------------");
                count++;
            }
        }
    }

    public static void freelancer(String[] args) {
        System.out.println("🔷 ========================= 🔷");
        FreelancerSide freelancer = new FreelancerSide();

        System.out.println("🎉 Welcome to Your Freelancer Dashboard!");
        Scanner input = new Scanner(System.in);

        System.out.println("[1] View Job Listings");
        System.out.println("[2] View Submitted Bids");
        System.out.print("➡️ Select an option: ");

        int response = input.nextInt();
        if (response == 1) {
            freelancer.jobList();
            freelancer.getJob();

            Scanner confirmation = new Scanner(System.in);
            System.out.print("\n➡️ To submit a bid, type 'submit': ");
            String submit = confirmation.next();

            if (submit.equalsIgnoreCase("submit")) {
                freelancer.submitBid();
                freelancer(args);
            }
        } else if (response == 2) {
            freelancer.getSubmittedBids();
            System.out.print("\n🔄 Return to dashboard? (yes/no): ");
            String goBack = input.next();
            if (goBack.equalsIgnoreCase("yes")) {
                freelancer(args);
            }
        } else {
            System.out.println("⚠️ Invalid option. Please try again.");
        }
    }
}
