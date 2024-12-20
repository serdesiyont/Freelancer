package dashboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.reflect.TypeToken;
import utilities.JobPosting;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static auth.Login.Email;

public class Client {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static String path = "src/main/java/utilities/jobpost.json";

    static Scanner input = new Scanner(System.in);



    public static void addJob(){
        List<String> requirements = new ArrayList<>();
        int id;

        System.out.print("Enter Title: ");
        String title = input.nextLine();
        System.out.print("Enter company name: ");
        String company = input.nextLine();
        System.out.print("Enter type: ");
        String type = input.nextLine();
        System.out.print("Enter salary: ");
        String salary = input.nextLine();

        System.out.print("Enter job description: ");
        String description = input.nextLine();

        System.out.print("Enter requirements: ");
        requirements.add(input.nextLine());



        List<JobPosting> jobsList = new ArrayList<>();

        try (FileReader reader = new FileReader(path)){
            File check = new File(path);
            if (check.length() != 0){
                Type jobsListType = new TypeToken<List<JobPosting>>() {}.getType();
               jobsList = gson.fromJson(reader, jobsListType);
                id = jobsList.getLast().getId() + 1;


                JobPosting newJob = new JobPosting(id, title, company,type, salary, description,Email, requirements);
                jobsList.add(newJob);


            }
            else{

               id = 1;

               JobPosting firstJob = new JobPosting(id, title, company,type, salary, description,Email, requirements);
                jobsList.add(firstJob);




            }
    reader.close();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }


        try(FileWriter writer = new FileWriter(path)){
            gson.toJson(jobsList, writer);
            System.out.println("Job Published successfully");
            writer.close();
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }

    }
    public static void managePublishedJobs(){

        try(FileReader reader = new FileReader(path)){
        Type jobsListType = new TypeToken<List<JobPosting>>() {}.getType();
        List<JobPosting> publishedJobs = gson.fromJson(reader, jobsListType);

        for(JobPosting Jobs: publishedJobs){
            if(Jobs.getPosted_by().equals(Email)){
                System.out.println(Jobs.getId()+" "+ Jobs.getTitle());
            }
            System.out.println("Enter id of the job");
            int id = input.nextInt();
            
        }

        reader.close();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }

    }



}
