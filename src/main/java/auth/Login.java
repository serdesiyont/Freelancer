package auth;
import auth.SignUp.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dashboard.*;


import java.io.FileReader;
import java.lang.ref.Cleaner;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.io.IOException;


public class Login {
    final String path = "src/main/java/auth/users.json";
    Scanner input = new Scanner(System.in);
    Gson gson = new Gson();

    public static String Email;


    public void loginUser(){
        System.out.print("Enter Your Email: ");
        String email = input.nextLine();

        System.out.print("Enter your password: ");
        String pass = input.nextLine();

        try(FileReader read = new FileReader(path)){
            Type userType = new TypeToken<List<User>>() {}.getType();
            List<User> userList = gson.fromJson(read, userType);

            for(User user1: userList){
                if(user1.email.equals(email) && user1.password.equals(pass)){
                    System.out.println("Welcome "+ user1.full_name);
                    if(Objects.equals(user1.account_type, "Freelancer")){
                        FreelancerSide.freelancer(new String[0]);
                    }else{
                        Email = user1.email;
                        System.out.println("""
                                Type 1: Dashboard
                                     2: Publish Job
                                """);
                        int choice = input.nextInt();

                        if (choice == 1){
                            System.out.println("Welcome to the dashboard");
                            Client.managePublishedJobs();
                        } else if (choice == 2) {

                        Client.addJob();
                        }
                        else {
                            System.out.println("Enter the correct number");
                        }

                    }
                }
            }

        }
        catch(IOException e){
            System.err.println("An error has occured" + e.getMessage());
        }

    }

}
