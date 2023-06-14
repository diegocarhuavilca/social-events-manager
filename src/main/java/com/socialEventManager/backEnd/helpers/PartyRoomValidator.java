package com.socialEventManager.backEnd.helpers;

import com.socialEventManager.backEnd.dto.PartyRoomDTO;
import com.socialEventManager.backEnd.dto.SocialEventTypeDTO;
import com.socialEventManager.backEnd.utils.EmptyFileException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class PartyRoomValidator {

    public static void allKeysNotEmptyValidator (PartyRoomDTO partyRoomDTO){
        if (partyRoomDTO.getName().isEmpty()) {
            throw new EmptyFileException("Name empty");
        }


        if (partyRoomDTO.getDescription().isEmpty()) {
            throw new EmptyFileException("Description empty");
        }

        if (partyRoomDTO.getAddress().isEmpty()) {
            throw new EmptyFileException("Address empty");
        }

        if (partyRoomDTO.getDistrict().isEmpty()) {
            throw new EmptyFileException("District empty");
        }

        if (Objects.requireNonNull(partyRoomDTO.getMainPhoto().getOriginalFilename()).isEmpty()) {
            throw new EmptyFileException("Icon empty");
        }

        for (MultipartFile file : partyRoomDTO.getSecondaryPhotos()) {
            if ( Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                throw new EmptyFileException("Photos empty");
            }
        }
    }
}
