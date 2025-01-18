package auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.Main;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SignUp {

    private static final Scanner input = new Scanner(System.in);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
    private final String path = "src/main/java/auth/users.json";

    static class User {
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
        System.out.println("\n=== User Registration ===");

        // Input full name
        System.out.print("Enter Full Name: ");
        String full_name = input.nextLine();

        // Input email
        System.out.print("Enter your Email: ");
        String email = input.nextLine();

        // Input password
        System.out.print("Enter your Password: ");
        String rawPassword = input.nextLine();
        String encryptedPassword = passwordEncryptor.encryptPassword(rawPassword);

        // Input account type with validation
        System.out.print("Choose account type:\n 1: Client\n 2: Freelancer\n👉 Enter your choice: ");
        int accountTypeChoice = input.nextInt();
        input.nextLine(); // Consume the leftover newline character
        while (accountTypeChoice != 1 && accountTypeChoice != 2) {
            System.out.print("❌ Invalid choice. Please enter 1 for Client or 2 for Freelancer: ");
            accountTypeChoice = input.nextInt();
            input.nextLine();
        }
        String account_type = (accountTypeChoice == 1) ? "Client" : "Freelancer";

        // Create a new user
        User newUser = new User();
        List<User> usersList = new ArrayList<>();

        File file = new File(path);
        if (file.exists() && file.length() != 0) {
            // Read existing users from file
            try (FileReader reader = new FileReader(path)) {
                Type userListType = new TypeToken<List<User>>() {}.getType();
                usersList = gson.fromJson(reader, userListType);

                // Check if the email is already taken
                for (User user : usersList) {
                    while (user.email.equals(email)) {
                        System.out.print("❌ Email is already taken. Enter a new email: ");
                        email = input.nextLine();
                    }
                }
            } catch (IOException e) {
                System.err.println("❌ Error reading user data: " + e.getMessage());
                return;
            }
        }

        // Add new user to the list
        newUser.createUser(full_name, email, encryptedPassword, account_type);
        usersList.add(newUser);

        // Save updated users list to file
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(usersList, writer);
            System.out.println("\n✅ User registered successfully!");
        } catch (IOException e) {
            System.err.println("❌ Error saving user data: " + e.getMessage());
        }
    }
}
