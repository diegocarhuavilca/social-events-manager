package com.socialEventManager.backEnd.controllers;

import com.socialEventManager.backEnd.models.PartyRoom;
import com.socialEventManager.backEnd.models.SocialEventType;
import com.socialEventManager.backEnd.services.PartyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("party-room")
public class PartyRoomController {

    @Autowired
    private PartyRoomService partyRoomService;

    @GetMapping
    public ResponseEntity <List<PartyRoom>> getAllPartyRooms (){
        return ResponseEntity.ok().body(partyRoomService.getAllPartyRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartyRoom> getPartyRoomById(@PathVariable String id){
        return ResponseEntity.ok().body(partyRoomService.getPartyRoomById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PartyRoom> deletePartyRoomById(@PathVariable String id){
        return ResponseEntity.ok().body(partyRoomService.deletePartyRoomById(id));
    }

    @PostMapping
    public ResponseEntity<PartyRoom> createPartyRoom(@RequestBody PartyRoom partyRoom){
        return ResponseEntity.ok().body(partyRoomService.createPartyRoom(partyRoom));
    }
}
