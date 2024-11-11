package org.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data

public class User {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String bio;
    private String displayName;
    private LocalDateTime createAt;

    public User( String userName, String password, String email, String bio, String displayName) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.displayName = displayName;

    }

    @Override
    public String toString() {
        return
                        "UserName :" + userName + "\t" +
                        "Password :" + "****" + "\t" +
                        "Email :" + email + "\t" +
                        "Bio :" + bio + "\t" +
                        "DisplayName :" + displayName;
    }
}
