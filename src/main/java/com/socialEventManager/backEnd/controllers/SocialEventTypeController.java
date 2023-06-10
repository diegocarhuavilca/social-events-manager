package com.socialEventManager.backEnd.controllers;

import com.google.api.gax.rpc.NotFoundException;
import com.socialEventManager.backEnd.models.SocialEventType;
import com.socialEventManager.backEnd.services.SocialEventTypeService;
import com.socialEventManager.backEnd.servicesImpl.FirebaseService;
import com.socialEventManager.backEnd.utils.EmptyFileException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("social-event-type")
public class SocialEventTypeController {

    private final FirebaseService firebaseService;
    @Autowired
    private SocialEventTypeService socialEventTypeService;


    @Autowired
    public SocialEventTypeController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @GetMapping
    public ResponseEntity<List<SocialEventType>> getAllSocialEventTypes() {
        return ResponseEntity.ok().body(socialEventTypeService.getAllSocialEventType());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialEventType> getSocialEventTypeById(@PathVariable String id) {
        return ResponseEntity.ok().body(socialEventTypeService.getSocialEventTypeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SocialEventType> deleteSocialEventTypeById(@PathVariable String id) {
        return ResponseEntity.ok().body(socialEventTypeService.deleteSocialEventTypeById(id));


    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SocialEventType createSocialEventType(@RequestParam("name") @NotNull String name, @RequestParam("description") @NotNull String description, @RequestParam("icon")  @NotNull MultipartFile icon, @RequestParam("photos")  @NotNull List<MultipartFile> photos, @RequestParam("videos")  @NotNull List<MultipartFile> videos) throws ExecutionException {

        if (name.isEmpty()) {
            throw new EmptyFileException("Name empty");
        }

        if (description.isEmpty()) {
            throw new EmptyFileException("Description empty");
        }


        if (Objects.requireNonNull(icon.getOriginalFilename()).isEmpty()) {
            throw new EmptyFileException("Icon empty");
        }

        for (MultipartFile file : photos) {
            if ( Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                throw new EmptyFileException("Photos empty");
            }
        }

        for (MultipartFile file : videos) {
            if ( Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
               throw new EmptyFileException("Video empty");
            }
        }

        List<String> listPhotos = new ArrayList<>();
        String iconUrl = "";

        try {
            CompletableFuture<String> imageUrlFuture = firebaseService.uploadImage(icon,"social-events/boda/icono");
            iconUrl = imageUrlFuture.get();
        } catch (Exception e) {
            throw new ExecutionException("Error",e);
        }

        for (MultipartFile file : photos) {
            try {
                CompletableFuture<String> imageUrlFuture = firebaseService.uploadImage(file,"social-events/boda/photos");
                String imageUrl = imageUrlFuture.get();
                listPhotos.add(imageUrl);
            } catch (Exception e) {
                throw new ExecutionException("Error",e);
            }
        }

        SocialEventType socialEventType = new SocialEventType(name,description,iconUrl,listPhotos,listPhotos);

        return ResponseEntity.ok().body(socialEventTypeService.createSocialEventType(socialEventType)).getBody();
    }

}
