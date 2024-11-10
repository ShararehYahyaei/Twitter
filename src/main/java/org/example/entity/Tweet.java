package org.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class Tweet {

    private Long id;
    private String content;
    private List<Tag> tags;
    private Long userId;
    private Long like;
    private Long dislike;
}
