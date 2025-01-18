package auth;

import auth.SignUp.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dashboard.Client;
import dashboard.FreelancerSide;
import main.Main;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Login {
    final String path = "src/main/java/auth/users.json";
    Scanner input = new Scanner(System.in);
    Gson gson = new Gson();
    StrongPasswordEncryptor decrypt = new StrongPasswordEncryptor();
    public static String Email;

    public void loginUser() {
        System.out.println("\n=== Login ===");
        System.out.print("🔹 Please enter your email address: ");
        String email = input.nextLine();

        System.out.print("🔹 Please enter your password: ");
        String pass = input.nextLine();

        try (FileReader read = new FileReader(path)) {
            Type userType = new TypeToken<List<User>>() {}.getType();
            List<User> userList = gson.fromJson(read, userType);

            boolean loginSuccessful = false;

            for (User user : userList) {
                if (user.email.equals(email) && decrypt.checkPassword(pass, user.password)) {
                    loginSuccessful = true;
                    System.out.println("\n✅ Welcome, " + user.full_name + "!");

                    if (Objects.equals(user.account_type, "Freelancer")) {
                        System.out.println("🔹 Redirecting to Freelancer Dashboard...");
                        Main main = Main.getInstance();
                        main.setEmail(email);
                        FreelancerSide.freelancer(new String[0]);
                    } else {
                        Email = user.email;
                        System.out.println("\n=== Client Menu ===");
                        System.out.println("1. Access Dashboard");
                        System.out.println("2. Publish a Job");
                        System.out.print("👉 Please select an option: ");
                        int choice = input.nextInt();

                        while (choice < 1 || choice > 2) {
                            System.out.print("❌ Invalid selection. Please enter 1 or 2: ");
                            choice = input.nextInt();
                        }

                        if (choice == 1) {
                            System.out.println("🔹 Navigating to the Dashboard...");
                            Client.managePublishedJobs();
                        } else {
                            System.out.println("🔹 Navigating to Publish a Job...");
                            Client.addJob();
                        }
                    }
                    break;
                }
            }

            if (!loginSuccessful) {
                System.out.println("❌ Invalid email or password. Please try again.");
            }

        } catch (IOException e) {
            System.err.println("❌ An error occurred while accessing user data: " + e.getMessage());
        }
    }
}
