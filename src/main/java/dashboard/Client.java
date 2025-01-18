package dashboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import utilities.JobPosting;
import utilities.BidSubmission;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static auth.Login.Email;

public class Client {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static String path = "src/main/java/utilities/jobpost.json";
    static Scanner input = new Scanner(System.in);

    public static void addJob() {
        List<String> requirements = new ArrayList<>();
        int id;

        System.out.println("\n=== Add a New Job Posting ===");
        System.out.print("🔹 Enter Job Title: ");
        String title = input.nextLine();
        System.out.print("🔹 Enter Company Name: ");
        String company = input.nextLine();
        System.out.print("🔹 Enter Job Type (e.g., Full-time, Part-time): ");
        String type = input.nextLine();
        System.out.print("🔹 Enter Salary: ");
        String salary = input.nextLine();
        System.out.print("🔹 Enter Job Description: ");
        String description = input.nextLine();
        System.out.print("🔹 Enter Job Requirements (comma-separated if multiple): ");
        requirements.add(input.nextLine());

        List<JobPosting> jobsList = new ArrayList<>();

        try (FileReader reader = new FileReader(path)) {
            File check = new File(path);
            if (check.length() != 0) {
                Type jobsListType = new TypeToken<List<JobPosting>>() {}.getType();
                jobsList = gson.fromJson(reader, jobsListType);
                id = jobsList.get(jobsList.size() - 1).getId() + 1;

                JobPosting newJob = new JobPosting(id, title, company, type, salary, description, Email, requirements);
                jobsList.add(newJob);
            } else {
                id = 1;
                JobPosting firstJob = new JobPosting(id, title, company, type, salary, description, Email, requirements);
                jobsList.add(firstJob);
            }
        } catch (IOException e) {
            System.err.println("❌ Error reading job postings: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(jobsList, writer);
            System.out.println("\n✅ Job Published Successfully!");
        } catch (IOException e) {
            System.err.println("❌ Error writing job postings: " + e.getMessage());
        }

        System.out.println("\nWhat would you like to do next?");
        System.out.println("1. Manage Published Jobs");
        System.out.println("2. Add Another Job");
        System.out.println("3. Exit");
        System.out.print("👉 Enter your choice: ");
        int nextAction = input.nextInt();
        input.nextLine(); // Consume newline

        switch (nextAction) {
            case 1 -> managePublishedJobs();
            case 2 -> addJob();
            default -> System.out.println("\n👋 Hope to see you again!");
        }
    }

    public static void managePublishedJobs() {
        System.out.println("\n=== Manage Your Published Jobs ===");

        try (FileReader reader = new FileReader(path)) {
            Type jobsListType = new TypeToken<List<JobPosting>>() {}.getType();
            List<JobPosting> publishedJobs = gson.fromJson(reader, jobsListType);

            System.out.println("\n🔍 Your Published Jobs:");
            for (JobPosting job : publishedJobs) {
                if (job.getPosted_by().equals(Email)) {
                    System.out.printf("   - [%d] %s\n", job.getId(), job.getTitle());
                }
            }

            System.out.print("\n👉 Enter the ID of the job to view its bids: ");
            int jobId = input.nextInt();
            input.nextLine(); // Consume newline

            String bidPath = "src/main/java/utilities/bids.json";
            FileReader bidReader = new FileReader(bidPath);
            List<BidSubmission> updatedBids = new ArrayList<>();
            Type bidsListType = new TypeToken<List<BidSubmission>>() {}.getType();
            List<BidSubmission> submittedBids = gson.fromJson(bidReader, bidsListType);
            HashMap<Integer, String> individualBids = new HashMap<>();

            System.out.println("\n📋 Bids for Job ID: " + jobId);
            int count = 0;
            for (BidSubmission bid : submittedBids) {
                if (bid.getId() == jobId) {
                    individualBids.put(++count, bid.getEmail());
                    System.out.printf("   [%d] Submitted by: %s\n", count, bid.getEmail());
                    System.out.println("       - Date: " + bid.getDate());
                    System.out.println("       - Experience: " + bid.getExperience());
                    System.out.println("       - Price: " + bid.getPrice());
                }
            }

            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Choose a Bid");
            System.out.println("2. Return to Dashboard");
            System.out.print("👉 Enter your choice: ");
            int choice = input.nextInt();

            while (choice != 1 && choice != 2) {
                System.out.print("❌ Invalid choice! Please enter 1 or 2: ");
                choice = input.nextInt();
            }

            if (choice == 1) {
                System.out.print("\n👉 Enter the Bid ID to select: ");
                int selectedBidId = input.nextInt();

                if (individualBids.containsKey(selectedBidId)) {
                    for (BidSubmission bid : submittedBids) {
                        if (bid.getId() == jobId) {
                            if (bid.getEmail().equals(individualBids.get(selectedBidId))) {
                                bid.setStatus("Success");
                                bid.setClientAddress(Email);
                            } else {
                                bid.setStatus("Rejected");
                            }
                            updatedBids.add(bid);
                        }
                    }
                    System.out.println("\n✅ Bid has been accepted! Contact the freelancer at: " + individualBids.get(selectedBidId));
                }
            } else {
                managePublishedJobs();
            }

            try (FileWriter bidWriter = new FileWriter(bidPath)) {
                gson.toJson(updatedBids, bidWriter);
            } catch (IOException e) {
                System.err.println("❌ Error writing updated bids: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("❌ Error reading job postings or bids: " + e.getMessage());
        }

        System.out.println("\nWhat would you like to do next?");
        System.out.println("1. Return to Dashboard");
        System.out.println("2. Publish Another Job");
        System.out.print("👉 Enter your choice: ");
        int nextAction = input.nextInt();
        input.nextLine(); // Consume newline

        if (nextAction == 1) {
            managePublishedJobs();
        } else if (nextAction == 2) {
            addJob();
        } else {
            System.out.println("\n👋 Hope to see you again!");
        }
    }
}
