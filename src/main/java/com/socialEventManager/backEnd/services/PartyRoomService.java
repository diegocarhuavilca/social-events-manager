package com.socialEventManager.backEnd.services;

import com.socialEventManager.backEnd.models.PartyRoom;

import java.util.List;

public interface PartyRoomService {

    List<PartyRoom> getAllPartyRooms();
    PartyRoom getPartyRoomById(String partyRoomId);
    PartyRoom createPartyRoom (PartyRoom partyRoom);
    PartyRoom deletePartyRoomById(String partyRoomId);
}
