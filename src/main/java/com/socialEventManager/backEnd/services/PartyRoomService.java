package com.socialEventManager.backEnd.services;

import com.socialEventManager.backEnd.dto.ExtraPartyRoomDTO;
import com.socialEventManager.backEnd.dto.PartyRoomDTO;
import com.socialEventManager.backEnd.dto.UpdatePartyRoomStatusDTO;
import com.socialEventManager.backEnd.models.ExtraPartyRoom;
import com.socialEventManager.backEnd.models.PartyRoom;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PartyRoomService {

    List<PartyRoom> getAllPartyRooms();
    PartyRoom getPartyRoomById(String partyRoomId);
    PartyRoom createPartyRoom (PartyRoomDTO partyRoom) throws IOException, InterruptedException, ExecutionException;
    PartyRoom deletePartyRoomById(String partyRoomId);
    List<ExtraPartyRoom> getAllExtrasPartyRoomById(String partyRoomId);
    ExtraPartyRoom getExtraPartyRoomByPartyRoomId(String partyRoomId, String extraPartyRoomId);
    List<ExtraPartyRoom> addExtraPartyRoom(String partyRoomId, ExtraPartyRoomDTO extraPartyRoom) throws IOException, InterruptedException, ExecutionException;
    PartyRoom updateStatus(String partyRoomId, UpdatePartyRoomStatusDTO updatePartyRoomStatusDTO);
}
