package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepository;

import java.sql.SQLException;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public UserService() {
    }

    public void signUp(String userName, String password, String email, String displayName, String bio) {
        String hashedPassword = hashPassword(password);
        User user = new User(userName, hashedPassword, email, bio, displayName);
        userRepository.save(user);


    }

    private String hashPassword(String password) {
        return password;
        //todo generate the hashcode
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
        User user = userRepository.login(userName, password);
        return user;
    }

    public User getInformationForOneUser(String email) {
        User user = userRepository.getInformationForOneUser(email);
        return user;
    }
    public User updateInformation(User user){
        System.out.println("gggggg");
       User newUser= userRepository.update(user);
      return newUser;
    }
}
