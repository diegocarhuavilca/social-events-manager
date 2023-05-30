package com.socialEventManager.backEnd.services;

import com.socialEventManager.backEnd.models.ExtraPartyRoom;
import com.socialEventManager.backEnd.models.PartyRoom;

import java.util.List;

public interface PartyRoomService {

    List<PartyRoom> getAllPartyRooms();
    PartyRoom getPartyRoomById(String partyRoomId);
    PartyRoom createPartyRoom (PartyRoom partyRoom);
    PartyRoom deletePartyRoomById(String partyRoomId);
    List<ExtraPartyRoom> getAllExtrasPartyRoomById(String partyRoomId);
    ExtraPartyRoom getExtraPartyRoomByPartyRoomId(String partyRoomId, String extraPartyRoomId);
    List<ExtraPartyRoom> addExtraPartyRoom(String partyRoomId,ExtraPartyRoom extraPartyRoom);
}
