package org.example.service;
import org.example.entity.Tag;
import org.example.repository.JunctionRepository;
import java.util.List;

public class JunctionService {
    JunctionRepository junctionRepository = new JunctionRepository();

    public void insert(Long tweetId, Long tagId) {
        junctionRepository.insert(tweetId, tagId);
    }

    public List<Tag> getTags(Long tweetId) {
        List<Tag> tags = junctionRepository.getTagsByTweetId(tweetId);
        return tags;

    }
    public void delete(Long tweetId, Long tagId) {
        junctionRepository.deleteWithTweetIdAndTagId(tweetId, tagId);
    }
}
