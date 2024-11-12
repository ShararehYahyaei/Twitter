package org.example.service;
import java.util.List;
import org.example.entity.Tag;
import org.example.repository.TagRepository;


public class TagService {
    TagRepository tagRepository;

    public TagService() {
        tagRepository = new TagRepository();
    }

    public void insertTags(String name) {
        tagRepository.insertTag(name);

    }

    public List<Tag> getTags() {
        List<Tag> tags = tagRepository.selectAllTags();
        return tags;
    }

    public String getTagName(Long id) {
        String name = tagRepository.getNameForTag(id);
        return name;
    }
}
