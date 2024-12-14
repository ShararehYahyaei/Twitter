package org.example.service;
import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    TweetService tweetService = new TweetService();

    public UserService() {
    }

    public void signUp(String userName, String password, String email, String displayName, String bio) {
        String hashedPassword = hashPassword(password);
        User user = new User(userName, hashedPassword, email, bio, displayName);
        userRepository.save(user);
    }

    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private boolean checkPassword(String password, String hashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
    }

    public boolean checkExistedUser(String userName) {
        boolean result = userRepository.checkExistedUser(userName);
        return result;
    }

    public boolean checkExistedUserByEmail(String email) {
        boolean res = userRepository.checkExistEmail(email);
        return res;
    }

    public User login(String userName, String password) {
        User user = userRepository.login(userName);
        String pasHash = user.getPassword();
        Boolean result = checkPassword(password, pasHash);
        if (result) {
            return user;
        }
        return null;
    }

    public User getInformationForOneUser(String email) {
        Optional<User> user = userRepository.getInformationForOneUser(email);
        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateInformation(User user) {
        Optional<User> newUser = userRepository.update(user);
        newUser.get();
        return newUser.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Tweet> getTweetUser(String email) {
        Long id = userRepository.getUserId(email);
        List<Tweet> tweets = tweetService.getContent(id);
        return tweets;
    }

    public String getInfo(Long userId) {
        String displayName = userRepository.getInfo(userId);
        return displayName;
    }

}
