package org.example;

import org.example.entity.Tag;
import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.exception.BusinessException;
import org.example.exception.MassageException;
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
    static User currentUser = null;
    public static void main(String[] args) {
        try {
            while (true) {
                String res = menu();
                switch (res) {
                    case "1":
                        signUp();
                        break;
                    case "2":
                        login();
                        break;
                    case "3":
                        System.exit(0);
                        break;
                    default:
                        throw new BusinessException(MassageException.wrongNumber);
                }

            }
        }catch (BusinessException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void logOut() {
        currentUser = null;
    }

    public static String menu() {
        System.out.println("Welcome To twitter");
        System.out.println("1 - Sign up ");
        System.out.println("2 - Login ");
        System.out.println("3 - Exit");
        System.out.println("Please enter your request : ");
        String result = new Scanner(System.in).nextLine();
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
            throw new BusinessException(MassageException.userExisted);

        }
        System.out.println(" 1 - Login ");
        System.out.println(" 2 - Exit ");
        System.out.println("Enter your request : ");
        String result = scanner.nextLine();
        if (result.equals("1")) {
            login();
        } else if (result.equals("2")) {
            System.exit(0);
        } else {
            throw new BusinessException(MassageException.wrongNumber);

        }
    }

    private static void login() {
        System.out.println("Please enter Your Email / UserName :");
        String userName = scanner.nextLine();
        System.out.println("Please enter Your Password :");
        String password = scanner.nextLine();
        User user = userService.login(userName, password);
        if (user != null) {
            System.out.println("login successful");
            currentUser = user;
            userMenu();
        } else {
            throw new BusinessException(MassageException.loginFailed);
        }

    }

    public static void userMenu() {
        while (true) {
            System.out.println(" 1 - New  Post ");
            System.out.println(" 2 - View Your Posts ");
            System.out.println(" 3 - Edit your profile ");
            System.out.println(" 4 - View all  posts ");
            System.out.println(" 5 - like or dislike ");
            System.out.println(" 6 - Retweet ");
            System.out.println(" 7 - Log Out ");
            System.out.println("please enter yor request : ");
            String res = new Scanner(System.in).nextLine();
            switch (res) {
                case "1":
                    createNewPost();
                    break;
                case "2":
                    System.out.println("your Tweet :");
                    List<Tweet> tweets = userService.getTweetUser(currentUser.getEmail());
                    for (int i = 0; i < tweets.size(); i++) {
                        System.out.println("Tweet Id :" + tweets.get(i).getId() + "\t" + " And Content :" + "\t" + tweets.get(i).getContent());
                    }
                    if (tweets.isEmpty()) {
                        throw new BusinessException(MassageException.notPostYet);
                    } else {
                        System.out.println(" 1- edit   your post ");
                        System.out.println(" 2- delete your post ");
                        String result = scanner.nextLine();
                        if (result.equals("1")) {
                            editTweet(tweets);
                        }
                        if (result.equals("2")) {
                            deleteYourTweet(currentUser.getEmail());
                        }
                    }

                    break;
                case "3":
                    User user = menuForUpdate(currentUser.getEmail());
                    User userN = updateUser(user);
                    if (userN != null) {
                        System.out.println("Your Data is updated");
                    } else {
                        System.out.println(MassageException.dateIsNotUpdated);
                    }
                    break;
                case "4":
                    getTweetsForAll();
                    break;
                case "5":
                    likeOrDislike();
                    break;
                case "6":
                    retweet(currentUser);
                    break;
                case "7":
                    logOut();
                    return;
            }
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
            throw new BusinessException(MassageException.wrongNumber);

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
            throw new BusinessException(MassageException.userNotFound);
        }

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

        List<Tag> chosenTags = chooseTags();
        tweetService.createNewTweet(post, chosenTags, currentUser);
        System.out.println("Post created successfully");
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
        throw new BusinessException(MassageException.tagNotFound);

    }

    public static void deleteYourTweet(String email) {
        List<Tweet> tweets = userService.getTweetUser(email);
        System.out.println("choose your id :");
        Long id = scanner.nextLong();
        for (int i = 0; i < tweets.size(); i++) {
            if (tweets.get(i).getId() == id) {
                tweetService.deleteTweet(id);
                System.out.println("Tweet deleted successfully");
                return;
            } else {
                throw new BusinessException(MassageException.tweetNotFound);
            }
        }
    }

    public static void getTweetsForAll() {
        List<Tweet> tweets = tweetService.getAllTweets();
        for (int i = 0; i < tweets.size(); i++) {
            if (tweets != null) {
                String display = userService.getInfo(tweets.get(i).getUserId());
                System.out.println("Id  " + tweets.get(i).getId() + " : " + " name : " + display + " *** " + "post : " + tweets.get(i).getContent());
            }
        }
    }

    public static void likeOrDislike() {
        List<Tweet> tweets = tweetService.getAllTweets();
        getTweetsForAll();
        System.out.println("choose your id :");
        Long id = scanner.nextLong();
        for (int i = 0; i < tweets.size(); i++) {
            if (tweets.get(i).getId() == id) {
                System.out.println(" 1 - like");
                System.out.println(" 2 - dislike");
                System.out.println("enter your comment : ");
                String comment = new Scanner(System.in).nextLine();
                if (comment.equals("1")) {
                    likeTweet(id);
                } else if (comment.equals("2")) {
                    dislike(id);
                } else {
                    throw new BusinessException(MassageException.wrongNumber);
                }

            }
        }

    }

    public static void likeTweet(Long tweetId) {
        boolean res = tweetService.likeTweet(tweetId);
        if (res) {
            System.out.println("Tweet Liked Successfully");
        }
    }

    public static void dislike(Long tweetId) {
        boolean res = tweetService.dislikeTweet(tweetId);
        if (res) {
            System.out.println("dislike Successfully");
        }
    }

    public static void retweet(User user) {
        List<Tweet> tweets = tweetService.getAllTweets();
        for (Tweet tweet : tweets) {
            System.out.println("Id : " + tweet.getId() + "\nContent : " + tweet.getContent());
        }
        System.out.println("please enter your id :");
        Long tweetId = scanner.nextLong();
        for (int i = 0; i < tweets.size(); i++) {
            if (tweets.get(i).getId() == tweetId) {
                String content = tweets.get(i).getContent();
                System.out.println(content);
                System.out.println("please enter content should be 280 characters :");
                String retweet = new Scanner(System.in).nextLine();
                String newR = retweet + " " + content;
                List<Tag> tags = junction.getTags(tweetId);
                tweetService.createNewTweet(newR, tags, user);
                System.out.println("retweet successfully");
            }
        }
    }

    public static void editTweet(List<Tweet> tweets) {
        for (int i = 0; i < tweets.size(); i++) {
            System.out.println("Tweet Id :" +
                    tweets.get(i).getId() + "\n" +
                    "content : " + tweets.get(i).getContent());

        }

        System.out.println("please choose your tweet id :");
        Long tweetId = new Scanner(System.in).nextLong();
        Tweet tweet;
        for (int i = 0; i < tweets.size(); i++) {
            if (tweets.get(i).getId() == tweetId) {
                tweet = tweets.get(i);
                break;
            } else {
                throw new BusinessException(MassageException.wrongId);
            }
        }
        System.out.println("please choose which one :");
        System.out.println(" 1 - content ");
        System.out.println(" 2 - Tag ");
        String res = new Scanner(System.in).nextLine();
        if (res.equals("1")) {
            String newComment = createContent();
            tweetService.updateContentByTweetId(newComment, tweetId);
            System.out.println("Content updated successfully");
        }
        if (res.equals("2")) {
            List<Tag> tags = junction.getTags(tweetId);
            if (tags.isEmpty()) {
                List<Tag> chosenTags = chooseTags();
                saveListTags(chosenTags, tweetId);
            } else {
                System.out.println(" 1 - delete tag ");
                System.out.println(" 2 - Add tag ");
                String finalT = new Scanner(System.in).nextLine();
                if (finalT.equals("1")) {
                    for (int i = 0; i < tags.size(); i++) {
                        String nameTag = tagService.getTagName(tags.get(i).getId());
                        System.out.println("Id Tag : " +
                                tags.get(i).getId() + "\n" +
                                "Tag Name : " + nameTag);
                    }
                    System.out.println("Please enter your Tag Id : ");
                    Long tagId = new Scanner(System.in).nextLong();
                    junction.delete(tweetId, tagId);
                    System.out.println("tag deleted successfully");

                }
                if (finalT.equals("2")) {
                    List<Tag> chosenTags = chooseTags();
                    saveListTags(chosenTags, tweetId);
                }
            }
        } else {
            throw new BusinessException(MassageException.wrongNumber);
        }
    }

    private static void saveListTags(List<Tag> chosenTags, Long tweetId) {
        for (int i = 0; i < chosenTags.size(); i++) {
            junction.insert(tweetId, chosenTags.get(i).getId());
        }
        System.out.println("your tags successfully");
    }
}

