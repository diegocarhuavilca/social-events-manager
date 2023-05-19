package com.socialEventManager.backEnd.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("partyRoom")
public class PartyRoom {

    @Id
    private String id;
    private String name;
    private String description;
    private String address;
    private String district;
    private String mainPhoto;
    private List<String> secondaryPhotos;
    private List<ExtraPartyRoom> extras;

    public PartyRoom(String name, String description, String address, String district, String mainPhoto, List<String> secondaryPhotos, List<ExtraPartyRoom> extras) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.district = district;
        this.mainPhoto = mainPhoto;
        this.secondaryPhotos = secondaryPhotos;
        this.extras = extras;
    }
}

