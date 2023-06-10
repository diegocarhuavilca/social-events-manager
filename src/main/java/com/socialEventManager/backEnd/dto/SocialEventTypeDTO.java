package com.socialEventManager.backEnd.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class SocialEventTypeDTO {
    private String name;
    private String description;
    private MultipartFile icon;
    private List<MultipartFile> photos;
    private List<MultipartFile>videos;

    public SocialEventTypeDTO(String name, String description, MultipartFile icon, List<MultipartFile> photos, List<MultipartFile> videos) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.photos = photos;
        this.videos = videos;
    }
}
