package main;

import auth.SignUp;
import auth.Login;
import dashboard.Client;
import dashboard.FreelancerSide;

import java.util.Scanner;
// while signup as client it goes to freelance dashoard
// back
public class Main {
    private static Main instance;
    private String email;
    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("=== Welcome to the Freelance Management System ===");

        while (true) {
            // Display the main menu
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Sign Up (Create an account)");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("👉 Enter your choice: ");
            int response = input.nextInt();
            input.nextLine(); // Consume the leftover newline character

            switch (response) {
                case 1:
                    // Sign up
                    SignUp signUp = new SignUp();
                    signUp.register();

                    System.out.print("\nWould you like to access your dashboard now? (Press 1 for Yes, any other key to exit): ");
                    int confirmation = input.nextInt();
                    input.nextLine(); // Consume newline
                    if (confirmation == 1) {
                        if (signUp.getAccountType().equals("Client")) {
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
                        } else if (signUp.getAccountType().equals("Freelancer")) {
                            FreelancerSide.freelancer(new String[0]);
                        }
                    }
                    break;

                case 2:
                    // Login
                    Login login = new Login();
                    login.loginUser();
                    break;

                case 3:
                    // Exit the application
                    System.out.println("\nThank you for using our system. Goodbye!");
                    input.close();
                    System.exit(0);

                default:
                    // Handle invalid input
                    System.out.println("❌ Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }
}