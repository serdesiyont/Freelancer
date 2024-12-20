package dashboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utilities.BidSubmission;
import utilities.JobPosting;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class FreelancerSide {
    String path = "src/main/java/utilities/jobpost.json";
    Scanner input = new Scanner(System.in);


    public void jobList() {
        try {
            System.out.println("Here are the Job lists: ");
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

    public void getJob(){
        System.out.println("#######################");
        System.out.print("For more detail select the desired choice: ");
        int index = input.nextInt();
        try {
            Gson gson = new Gson();
            String json = new String(Files.readAllBytes(Paths.get(path)));

            Type listType = new TypeToken<List<JobPosting>>() {}.getType();
            List<JobPosting> jobList = gson.fromJson(json, listType);

            if (index >= 1 && index-1 < jobList.size()) {
                JobPosting job = jobList.get(index-1);

                System.out.println("Title: " + job.getTitle());
                System.out.println("Company: " + job.getCompany());
                System.out.println("Type: " + job.getType());
                System.out.println("Salary: " + job.getSalary());
                System.out.println("Description: " + job.getDescription());
                System.out.println("Requirements: " + job.getRequirements());
            } else {
                System.out.println("Choose have chosen an invalid job, Please try again :(.");
            }

        } catch (Exception e) {
            System.out.println("Error reading the JSON file: " + e.getMessage());
        }

    }

    public void submitBid() {
        Scanner response = new Scanner(System.in);

        System.out.println("To submit your proposal answer the following questions: ");
        System.out.print("Description about your past experience that can be relatable to the current job application: ");
        String experience = response.next();
        System.out.print("Enter the price to complete the project: ");
        int price = response.nextInt();

        try {
            Gson gson = new Gson();
            String json = new String(Files.readAllBytes(Paths.get(path)));

            Type listType = new TypeToken<List<BidSubmission>>() {}.getType();
            List<JobPosting> jobList = gson.fromJson(json, listType);

        } catch (Exception e) {
            System.out.println("Error reading the JSON file: " + e.getMessage());
        }


    }

    public static void freelancer(String[] args) {
        FreelancerSide freelancer = new FreelancerSide();

        System.out.println("Welcome to your dashboard :).");
        Scanner input = new Scanner(System.in);

        System.out.println("1. JobList");
        System.out.println("2. ");
        System.out.print("Choose to were to redirect: ");

        int response = input.nextInt();
        if (response == 1){
            freelancer.jobList();
            freelancer.getJob();
//
            Scanner confirmation = new Scanner(System.in);
            System.out.print("To submit a bid, Type submit: ");
            String submit = confirmation.next();

            if (submit.toLowerCase() == "submit") {
                freelancer.submitBid();
            }
        }
        else {
            System.out.println("You have chose an invalid option, Please try again :(.");
        }

    }
}