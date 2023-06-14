package com.socialEventManager.backEnd.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
public class ExtraPartyRoomDTO {
    private String name;
    private String description;
    private MultipartFile mainPhoto;
    private List<MultipartFile> secondaryPhotos;

    public ExtraPartyRoomDTO(String name, String description, MultipartFile mainPhoto, List<MultipartFile> secondaryPhotos) {
        this.name = name;
        this.description = description;
        this.mainPhoto = mainPhoto;
        this.secondaryPhotos = secondaryPhotos;
    }
}
