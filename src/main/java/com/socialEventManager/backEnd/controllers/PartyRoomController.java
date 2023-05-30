package com.socialEventManager.backEnd.controllers;

import com.socialEventManager.backEnd.models.ExtraPartyRoom;
import com.socialEventManager.backEnd.models.PartyRoom;
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

    @GetMapping("/{partyRoomId}/extras")
    public ResponseEntity<List<ExtraPartyRoom>> getExtrasPartyRoomById (@PathVariable String partyRoomId){
        return ResponseEntity.ok().body(partyRoomService.getAllExtrasPartyRoomById(partyRoomId));
    }

    @GetMapping("/{partyRoomId}/extras/{partyRoomExtraId}")
    public ResponseEntity<ExtraPartyRoom> getExtraByIdAndPartyRoomById (@PathVariable String partyRoomId, @PathVariable String partyRoomExtraId){
        return ResponseEntity.ok().body(partyRoomService.getExtraPartyRoomByPartyRoomId(partyRoomId,partyRoomExtraId));
    }

    @PostMapping("/{partyRoomId}/extras")
    public ResponseEntity<List<ExtraPartyRoom>> addExtraPartyRoom (@PathVariable String partyRoomId, @RequestBody ExtraPartyRoom extraPartyRoom){
        return ResponseEntity.ok().body(partyRoomService.addExtraPartyRoom(partyRoomId,extraPartyRoom));
    }

}
