package main;
import auth.signup;
import auth.login;

import java.io.FileNotFoundException;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args){

//        signup test = new signup();
//        test.register();

        login test = new login();
        test.loginUser();

    }
}