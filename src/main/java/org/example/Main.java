package org.example;

import org.example.entity.Tag;
import org.example.entity.User;
import org.example.service.JunctionService;
import org.example.service.TagService;
import org.example.service.TweetService;
import org.example.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static UserService userService = new UserService();
    static TagService tagService = new TagService();
    static TweetService tweetService = new TweetService();
    static JunctionService junction = new JunctionService();

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
        System.out.println("3 - Exit");
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
            userMenu();
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

    public static void userMenu() {
        System.out.println(" 1 - New  Post ");
        System.out.println(" 2 - View Post ");
        System.out.println(" 3 - Edit Your Profile ");
        System.out.println(" 4 - Exit ");
        String res = scanner.nextLine();
        switch (res) {
            case "1":
                createNewPost();


                break;
            case "2":
                //todo view posts
                break;
            case "3":
                System.out.println("Please enter your email");
                String email = scanner.nextLine();
                User user = menuForUpdate(email);
                User userN = updateUser(user);
                if (userN != null) {
                    System.out.println("Your Data is updated");
                } else {
                    System.out.println("Your Data is not updated");
                }
                break;
            case "4":
                System.exit(0);
                break;

        }
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
        }
        if (result.equals("5")) {
            System.exit(0);
            return null;
        } else {
            System.out.println("Wrong Number");
            return null;
        }

    }

    public static User updateUser(User user) {
        User newUser = userService.updateInformation(user);
        return newUser;
    }

    public static User userInformation(String email) {
        User user = userService.getInformationForOneUser(email);
        if (user != null) {
            System.out.println(user);
            return user;
        } else {
            System.out.println("User not found");
            menu();
        }
        return user;
    }

    public static void createNewTag() {
        System.out.println("Please enter your tag :");
        String tag = scanner.nextLine();
        tagService.insertTags(tag);
        System.out.println("Tag created successfully");

    }

    public static void createNewPost() {
        String post = "";
        for (int i = 0; i < 3; i++) {
            String content = createContent();
            if (content.length() < 280) {
                post = content;
                break;
            }
        }

      List<Tag>chosenTags= chooseTags();



    }

    private static List<Tag> chooseTags() {
        List<Tag> tags = new ArrayList<Tag>();
        for (int i = 0; i < 10; i++) {
            List<Tag> showTags = showAllTags();
            System.out.println(" 1 - Create a new tag");
            System.out.println(" 2 - Please enter Tag id :");
            System.out.println(" 3 - Exit ");
            String resultF = scanner.nextLine();
            if (resultF.equals("1")) {
                createNewTag();
            }
            if (resultF.equals("2")) {
                Tag tag = chooseTag(showTags);
                if (tag != null) {
                    tags.add(tag);
                }
            }
            if (resultF.equals("3")) {
                return tags;
            }

        }
        return tags;
    }


    public static String createContent() {
        System.out.println("Please enter your content  :");
        System.out.println("The content should be less than 280 characters");
        String post = scanner.nextLine();
        return post;
    }

    public static List<Tag> showAllTags() {
        List<Tag> tags = tagService.getTags();
        for (int i = 0; i < tags.size(); i++) {
            if (i % 5 == 0) {
                System.out.println();
            }
            System.out.print(tags.get(i).getId() + " : " + tags.get(i).getName() + "\t");
        }
        System.out.println();
        return tags;
    }


    public static Tag chooseTag(List<Tag> tags) {
        System.out.println("Please enter your tag id :");
        Long id = scanner.nextLong();
        for (Tag tag : tags) {
            if (tag.getId() == id) {
                return tag;
            }
        }
        System.out.println("Tag not found");
        return null;
    }

}