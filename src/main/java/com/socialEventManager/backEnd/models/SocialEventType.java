package com.socialEventManager.backEnd.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("socialEventType")
public class SocialEventType {
    @Id
    private String id;
    private String name;
    private String description;
    private String icon;
    private List<String> photos;
    private List<String> videos;

    public SocialEventType(String name, String description, String icon, List<String> photos, List<String> videos) {
        super();
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.photos = photos;
        this.videos = videos;
    }
}
