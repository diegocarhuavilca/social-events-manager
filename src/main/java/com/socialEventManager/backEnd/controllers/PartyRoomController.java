package com.socialEventManager.backEnd.controllers;

import com.socialEventManager.backEnd.dto.ExtraPartyRoomDTO;
import com.socialEventManager.backEnd.dto.PartyRoomDTO;
import com.socialEventManager.backEnd.dto.UpdatePartyRoomStatusDTO;
import com.socialEventManager.backEnd.models.ExtraPartyRoom;
import com.socialEventManager.backEnd.models.PartyRoom;
import com.socialEventManager.backEnd.services.PartyRoomService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
@CrossOrigin
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
    public ResponseEntity<PartyRoom> createPartyRoom(@RequestParam("name") @NotNull String name, @RequestParam("description") @NotNull String description, @RequestParam("address") @NotNull String address, @RequestParam("district") @NotNull String district,@RequestParam("mainPhoto")  @NotNull MultipartFile mainPhoto, @RequestParam("secondaryPhotos")  @NotNull List<MultipartFile> secondaryPhotos) throws IOException, ExecutionException, InterruptedException {
        PartyRoomDTO partyRoomDTO = new PartyRoomDTO(name,description,address,district,mainPhoto,secondaryPhotos,true);
        return ResponseEntity.ok().body(partyRoomService.createPartyRoom(partyRoomDTO));
    }

    @PostMapping("/{partyRoomId}/status")
    public  ResponseEntity<PartyRoom> updateStatusPartyRoom (@PathVariable String partyRoomId,  @RequestBody @NotNull UpdatePartyRoomStatusDTO updatePartyRoomStatusDTO){
        return  ResponseEntity.ok().body(partyRoomService.updateStatus(partyRoomId, updatePartyRoomStatusDTO));
    };

    @GetMapping("/{partyRoomId}/extras")
    public ResponseEntity<List<ExtraPartyRoom>> getExtrasPartyRoomById (@PathVariable String partyRoomId){
        return ResponseEntity.ok().body(partyRoomService.getAllExtrasPartyRoomById(partyRoomId));
    }

    @GetMapping("/{partyRoomId}/extras/{partyRoomExtraId}")
    public ResponseEntity<ExtraPartyRoom> getExtraByIdAndPartyRoomById (@PathVariable String partyRoomId, @PathVariable String partyRoomExtraId){
        return ResponseEntity.ok().body(partyRoomService.getExtraPartyRoomByPartyRoomId(partyRoomId,partyRoomExtraId));
    }

    @PostMapping("/{partyRoomId}/extras")
    public ResponseEntity<List<ExtraPartyRoom>> addExtraPartyRoom (@PathVariable String partyRoomId,@RequestParam("name") @NotNull String name, @RequestParam("description") @NotNull String description,@RequestParam("mainPhoto")  @NotNull MultipartFile mainPhoto, @RequestParam("secondaryPhotos")  @NotNull List<MultipartFile> secondaryPhotos) throws IOException, ExecutionException, InterruptedException {
        ExtraPartyRoomDTO extraPartyRoom = new ExtraPartyRoomDTO(name,description,mainPhoto,secondaryPhotos);
        return ResponseEntity.ok().body(partyRoomService.addExtraPartyRoom(partyRoomId,extraPartyRoom));
    }

}
