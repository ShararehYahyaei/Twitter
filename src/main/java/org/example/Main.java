package org.example;

import org.example.entity.User;
import org.example.service.UserService;

import javax.imageio.plugins.tiff.ExifTIFFTagSet;
import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static UserService userService = new UserService();

    public static void main(String[] args) {

        String res = menu();
        switch (res) {
            case "1":
                signUp();
                break;
            case "2":
                signIn();
                break;
            case "3":
                System.out.println("Please enter your email");
                String email = scanner.nextLine();
                User user = menuForUpdate(email);
                User userN=  updateUser(user);
                if(userN != null) {
                    System.out.println("Your Data is updated");
                }else{
                    System.out.println("Your Data is not updated");
                }
                break;
            case "4":
                System.exit(0);
                break;
            case "5":
                System.out.println("Wrong Number");
                break;
        }


    }


    public static String menu() {
        System.out.println("Welcome To twitter");
        System.out.println("1 - Sign up ");
        System.out.println("2 - Sign in  ");
        System.out.println("3 - Edit Your Profile");
        System.out.println("4 - Exit");
        System.out.println("Please enter your request : ");
        String result = scanner.nextLine();
        return result;

    }


    public static void signUp() {
        System.out.println("Please enter Your userName :");
        String userName = scanner.nextLine();
        System.out.println("Please enter Your Password :");
        String password = scanner.nextLine();
        System.out.println("Please enter Your Email :");
        String email = scanner.nextLine();
        System.out.println("Please enter Your DisplayName :");
        String displayName = scanner.nextLine();
        System.out.println("Please enter Your Bio :");
        String bio = scanner.nextLine();
        if (userService.checkExistedUser(userName) && userService.checkExistedUserByEmail(email)) {
            userService.signUp(userName, password, email, displayName, bio);
            System.out.println("sign up successful");
        } else {
            System.out.println("you have already registered");
        }
        System.out.println(" 1 - Login ");
        System.out.println(" 2 - Exit ");
        System.out.println("Enter your request : ");
        String result = scanner.nextLine();
        if (result.equals("1")) {
            signIn();
        } else {
            System.exit(0);
        }

    }

    private static void signIn() {
        System.out.println("Please enter Your Email / UserName :");
        String userName = scanner.nextLine();
        System.out.println("Please enter Your Password :");
        String password = scanner.nextLine();
        User user = userService.login(userName, password);
        if (user != null) {
            System.out.println("login successful");
        } else {
            System.out.println("login failed");
            System.out.println(" 1 - Sign in ");
            System.out.println(" 2 - Exit ");
            System.out.println("Enter your request : ");
            String result = scanner.nextLine();
            if (result.equals("1")) {
                signIn();
            } else {
                System.exit(0);
            }
        }

    }

    public void showMenuAfterLogin() {
        System.out.println(" 1 - New  Post ");
        System.out.println(" 2 - View Post ");
    }

    public static User menuForUpdate(String email) {
        User user = userInformation(email);
        System.out.println(" 1 - Update Your UserName : ");
        System.out.println(" 2 - Update Your Password : ");
        System.out.println(" 3 - Update Your Bio : ");
        System.out.println(" 4 - Update Your DisplayName : ");
        System.out.println(" 5 -Exit ");
        System.out.println("Please Enter your request : ");
        String result = scanner.nextLine();
        if (result.equals("1")) {
            System.out.println("Enter your Name :");
            String userName = scanner.nextLine();
            user.setUserName(userName);
            return user;
        }
        if (result.equals("2")) {
            System.out.println("Enter your Password :");
            String password = scanner.nextLine();
            user.setPassword(password);
            return user;
        }
        if (result.equals("3")) {
            System.out.println("Enter your Bio :");
            String bio = scanner.nextLine();
            user.setBio(bio);
            return user;
        }
        if (result.equals("4")) {
            System.out.println("Enter your DisplayName :");
            String displayName = scanner.nextLine();
            user.setDisplayName(displayName);
            return user;
        } else {
            return null;
        }
    }

    public static User updateUser(User user) {
        User newUser = userService.updateInformation(user);
        return newUser;
    }

    public static User userInformation(String email) {
        User user = userService.getInformationForOneUser(email);
        if(user!=null){
            System.out.println(user);
            return user;
        }else{
            System.out.println("User not found");
            menu();
        }
        return user;
    }

}