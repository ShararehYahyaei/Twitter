package org.example.service;

import org.example.entity.Tag;
import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.repository.JunctionRepository;
import org.example.repository.TweetRepository;
import org.example.repository.UserRepository;

import java.util.List;

public class TweetService {
    static TweetRepository tweetRepository = new TweetRepository();
    JunctionService junctionService = new JunctionService();

    public void createNewTweet(String content, List<Tag> tags, User user) {
        Long id = tweetRepository.insertTweet(content, user);
        for (Tag tag : tags) {
            Long tagId = tag.getId();
            junctionService.insert(id, tagId);
        }


    }

    public List<Tweet> getContent(Long id) {
        List<Tweet> tweets = tweetRepository.getContentForUser(id);
        return tweets;
    }

    public boolean deleteTweet(Long id) {
        boolean res = tweetRepository.deleteTweet(id);
        return res;
    }

    public List<Tweet> getAllTweets() {
        List<Tweet> tweets = tweetRepository.getTweetsForAllUsers();
        return tweets;
    }

    public boolean likeTweet(Long tweetId) {
        boolean res = tweetRepository.updateLike(tweetId);
        return res;
    }

    public boolean dislikeTweet(Long tweetId) {
        boolean res = tweetRepository.updateDislike(tweetId);
        return res;
    }
    public void updateContentByTweetId(String comment, Long tweetId) {
        tweetRepository.updateContentByTweetId(comment, tweetId);
    }

}
