package auth;
import auth.signup.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.Freelancer_side;


import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.io.IOException;


public class login {
    final String path = "src/main/java/auth/users.json";
    Scanner input = new Scanner(System.in);
    Gson gson = new Gson();
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
                        Freelancer_side.main(new String[0]);
                    }
                }
            }

        }
        catch(IOException e){
            System.err.println("An error has occured" + e.getMessage());
        }

    }

}
