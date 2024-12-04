package auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

import java.util.Scanner;


public class signup {

    private static final Scanner input = new Scanner(System.in);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    final String path = "src/main/java/auth/users.json";

     private static class User{
     String full_name;
     String email;
     String password;

    // Method to set user details
    public void createUser(String full_name, String email, String password) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
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

        User user = new User();
        user.createUser(full_name, email, password);

        File check = new File(path);
        List<User> usersList = new ArrayList<>();
        try(FileReader reader = new FileReader(path)){
            if (check.length() != 0){

                Type usersListType = new TypeToken<List<User>>() {}.getType();
                usersList = gson.fromJson(reader, usersListType);

                usersList.add(user);



            }
            else{


                usersList.add(user);

            }
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }



        try (FileWriter writer = new FileWriter(path) ){

            gson.toJson(usersList, writer);

            System.out.println("User Saved successfully");
        } catch (IOException e) {
            System.err.println("Error writing user data to file: " + e.getMessage());
        }
    }

}
