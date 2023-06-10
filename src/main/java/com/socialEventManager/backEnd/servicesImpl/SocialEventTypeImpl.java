package com.socialEventManager.backEnd.servicesImpl;

import com.socialEventManager.backEnd.dto.SocialEventTypeDTO;
import com.socialEventManager.backEnd.helpers.SocialEventTypeValidator;
import com.socialEventManager.backEnd.models.SocialEventType;
import com.socialEventManager.backEnd.repositories.SocialEventTypeRepository;
import com.socialEventManager.backEnd.services.SocialEventTypeService;
import com.socialEventManager.backEnd.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class SocialEventTypeImpl implements SocialEventTypeService {

    @Autowired
    private SocialEventTypeRepository socialEventTypeRepository;

    @Autowired
    private FirebaseService firebaseService;

    @Override
    public List<SocialEventType> getAllSocialEventType () {
        return this.socialEventTypeRepository.findAll();
    }

    @Override
    public SocialEventType getSocialEventTypeById (String socialEventTypeId) {
        Optional < SocialEventType > SocialEventTypeDb = this.socialEventTypeRepository.findById(socialEventTypeId);

        if (SocialEventTypeDb.isPresent()) {
            return SocialEventTypeDb.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + socialEventTypeId);
        }
    }

    @Override
    public SocialEventType createSocialEventType(SocialEventTypeDTO socialEventTypeDTO) throws ExecutionException, IOException, InterruptedException {
        SocialEventTypeValidator.allKeysNotEmptyValidator(socialEventTypeDTO);

        String iconUrl = firebaseService.uploadImage(socialEventTypeDTO.getIcon(), "social-events/"+socialEventTypeDTO.getName()+"/icono").get();

        List<String> listPhotos = socialEventTypeDTO.getPhotos().stream()
                .map(file -> {
                    try {
                        return firebaseService.uploadImage(file, "social-events/"+socialEventTypeDTO.getName()+"/photos").get();
                    } catch (Exception e) {
                        throw new RuntimeException("Error uploading photo: " + file.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.toList());

        SocialEventType socialEventType = new SocialEventType(
                socialEventTypeDTO.getName(),
                socialEventTypeDTO.getDescription(),
                iconUrl,
                listPhotos,
                listPhotos
        );

        return this.socialEventTypeRepository.save(socialEventType);
    }

//    @Override
//    public SocialEventType createSocialEventType(SocialEventType socialEventType){
//        return this.socialEventTypeRepository.save(socialEventType);
//    }



    @Override
    public SocialEventType deleteSocialEventTypeById(String socialEventTypeId){
        Optional<SocialEventType> socialEventFound = this.socialEventTypeRepository.findById(socialEventTypeId);
        if(socialEventFound.isPresent()){
            this.socialEventTypeRepository.deleteById(socialEventTypeId);
            return socialEventFound.get();
        }
        else{
            throw new ResourceNotFoundException("Record not found with id : " + socialEventTypeId);
        }
    }
}
