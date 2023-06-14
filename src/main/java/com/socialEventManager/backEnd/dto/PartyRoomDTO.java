package com.socialEventManager.backEnd.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PartyRoomDTO {
    private String name;
    private String description;
    private String address;
    private String district;
    private MultipartFile mainPhoto;
    private List<MultipartFile> secondaryPhotos;
    private Boolean active;


    public PartyRoomDTO(String name, String description, String address, String district, MultipartFile mainPhoto, List<MultipartFile> secondaryPhotos, Boolean active) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.district = district;
        this.mainPhoto = mainPhoto;
        this.secondaryPhotos = secondaryPhotos;
        this.active = active;
    }
}
