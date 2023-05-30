package com.socialEventManager.backEnd.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class ExtraPartyRoom {
    @Id
    private String id;
    private String name;
    private String description;
    private String mainPhoto;
    private List<String> secondaryPhotos;

    public ExtraPartyRoom(String id, String name, String description, String mainPhoto, List<String> secondaryPhotos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.mainPhoto = mainPhoto;
        this.secondaryPhotos = secondaryPhotos;
    }
}
