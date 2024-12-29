package auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

import java.util.Scanner;


public class SignUp {

    private static final Scanner input = new Scanner(System.in);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    final String path = "src/main/java/auth/users.json";

    static class User{
        String full_name;
        String email;
        String password;
        String account_type;

        // Method to set user details
        public void createUser(String full_name, String email, String password, String account_type) {
            this.full_name = full_name;
            this.email = email;
            this.password = password;
            this.account_type = account_type;
        }


    }


    // Method to register a user
    public void register() {
        System.out.print("Enter Full Name: ");
        String full_name = input.nextLine();

        System.out.print("Enter your Email: ");
        String email = input.nextLine();

        System.out.print("Enter your Password: ");
        String password = input.nextLine();

        System.out.print("Choose account type: \n 1, Client or 2, Freelancer: ");
        String account_type = "";
        int type = input.nextInt();
        input.nextLine();
        if (type == 1){
            account_type += "Client";
        } else if (type == 2) {
            account_type += "Freelancer";
        }

        User user = new User();

        File check = new File(path);
        List<User> usersList = new ArrayList<>();
        try(FileReader reader = new FileReader(path)){
            if (check.length() != 0){

                Type usersListType = new TypeToken<List<User>>() {}.getType();
                usersList = gson.fromJson(reader, usersListType);

                for(User user1 : usersList){
                    while (user1.email.equals(email)){
                        System.out.println("Email taken enter a new one: ");
                        email = input.nextLine();
                    }
                }
                user.createUser(full_name, email, password, account_type);

                usersList.add(user);

            }
            else{
                user.createUser(full_name, email, password, account_type);
                usersList.add(user);

                Main mainClass = new Main();
                mainClass.setEmail(email);

            }
            reader.close();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }


        try (FileWriter writer = new FileWriter(path) ){

            gson.toJson(usersList, writer);

            System.out.println("User Saved successfully");
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing user data to file: " + e.getMessage());
        }
    }

}
