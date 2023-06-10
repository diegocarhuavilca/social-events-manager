package com.socialEventManager.backEnd.helpers;

import com.socialEventManager.backEnd.dto.SocialEventTypeDTO;
import com.socialEventManager.backEnd.utils.EmptyFileException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class SocialEventTypeValidator {

    public static void allKeysNotEmptyValidator (SocialEventTypeDTO socialEventTypeDTO){
        if (socialEventTypeDTO.getName().isEmpty()) {
            throw new EmptyFileException("Name empty");
        }

        if (socialEventTypeDTO.getDescription().isEmpty()) {
            throw new EmptyFileException("Description empty");
        }


        if (Objects.requireNonNull(socialEventTypeDTO.getIcon().getOriginalFilename()).isEmpty()) {
            throw new EmptyFileException("Icon empty");
        }

        for (MultipartFile file : socialEventTypeDTO.getPhotos()) {
            if ( Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                throw new EmptyFileException("Photos empty");
            }
        }

        for (MultipartFile file : socialEventTypeDTO.getVideos()) {
            if ( Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                throw new EmptyFileException("Video empty");
            }
        }
    }
}
