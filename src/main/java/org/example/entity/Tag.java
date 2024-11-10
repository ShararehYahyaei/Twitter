package org.example.entity;

import lombok.Data;

@Data
public class Tag {
    private Long id;
    private String name;

    public Tag(String name,Long id) {
        this.name = name;
        this.id = id;
    }


}
