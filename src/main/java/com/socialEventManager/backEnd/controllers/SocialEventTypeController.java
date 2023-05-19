package com.socialEventManager.backEnd.controllers;

import com.socialEventManager.backEnd.models.SocialEventType;
import com.socialEventManager.backEnd.services.SocialEventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("social-event-type")
public class SocialEventTypeController {

    @Autowired
    private SocialEventTypeService socialEventTypeService;

    @GetMapping
    public ResponseEntity <List<SocialEventType>> getAllSocialEventTypes (){
        return ResponseEntity.ok().body(socialEventTypeService.getAllSocialEventType());
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
