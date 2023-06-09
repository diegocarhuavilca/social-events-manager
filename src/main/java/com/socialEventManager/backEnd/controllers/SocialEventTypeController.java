package com.socialEventManager.backEnd.controllers;

import com.socialEventManager.backEnd.configs.FirebaseConfig;
import com.socialEventManager.backEnd.models.SocialEventType;
import com.socialEventManager.backEnd.services.FirebaseService;
import com.socialEventManager.backEnd.services.SocialEventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("social-event-type")
public class SocialEventTypeController {

    @Autowired
    private SocialEventTypeService socialEventTypeService;
    private FirebaseService firebaseService;


    @Autowired
    public SocialEventTypeController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @GetMapping
    public ResponseEntity <List<SocialEventType>> getAllSocialEventTypes (){
        return ResponseEntity.ok().body(socialEventTypeService.getAllSocialEventType());
    }

    @PostMapping("/fire")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = firebaseService.uploadImage(file);
            return "Image uploaded successfully. Image URL: " + imageUrl;
        } catch (Exception e) {
            return "Image upload failed. Error: " + e.getMessage();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialEventType> getSocialEventTypeById(@PathVariable String id){
        return ResponseEntity.ok().body(socialEventTypeService.getSocialEventTypeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SocialEventType> deleteSocialEventTypeById(@PathVariable String id){
        return ResponseEntity.ok().body(socialEventTypeService.deleteSocialEventTypeById(id));
    }

    @PostMapping
    public ResponseEntity<SocialEventType> createSocialEventType(@RequestBody SocialEventType socialEventType){
        return ResponseEntity.ok().body(socialEventTypeService.createSocialEventType(socialEventType));
    }

}
