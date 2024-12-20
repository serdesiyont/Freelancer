package main;
import auth.SignUp;
import auth.Login;

import dashboard.FreelancerSide;

import java.util.Scanner;


public class Main {

    private String fullName;
    private String email;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public  String getFullName() {
        return this.fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("Create User or Login: ");
        System.out.println("1. Sign Up");
        System.out.println("2. Login");

        int response = input.nextInt();

        if (response == 1) {
            SignUp test = new SignUp();
            test.register();
            System.out.println("Access your dashboard? Press 1 to continue");
            int confirmation = input.nextInt();
            if (confirmation == 1) {
                FreelancerSide.freelancer(new String[0]);
            }
        } else if (response == 2) {
            Login test = new Login();
            test.loginUser();
        } else {
            System.out.println("You have chose an invalid option, Please Try again :).");
        }



    }
}