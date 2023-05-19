package com.socialEventManager.backEnd.servicesImpl;

import com.socialEventManager.backEnd.models.PartyRoom;
import com.socialEventManager.backEnd.models.SocialEventType;
import com.socialEventManager.backEnd.repositories.PartyRoomRepository;
import com.socialEventManager.backEnd.repositories.SocialEventTypeRepository;
import com.socialEventManager.backEnd.services.PartyRoomService;
import com.socialEventManager.backEnd.services.SocialEventTypeService;
import com.socialEventManager.backEnd.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PartyRoomImpl implements PartyRoomService {

    @Autowired
    private PartyRoomRepository partyRoomRepository;

    @Override
    public List<PartyRoom> getAllPartyRooms() {
        return this.partyRoomRepository.findAll();
    }

    @Override
    public PartyRoom getPartyRoomById(String partyRoomId) {
        Optional<PartyRoom> partyRoomDb = this.partyRoomRepository.findById(partyRoomId);

        if(partyRoomDb.isPresent()){
            return partyRoomDb.get();
        }
        else{
            throw new ResourceNotFoundException("Record not found with id : " + partyRoomId);
        }

    }

    @Override
    public PartyRoom createPartyRoom(PartyRoom partyRoom) {
        return this.partyRoomRepository.save(partyRoom);
    }

    @Override
    public PartyRoom deletePartyRoomById(String partyRoomId) {
        Optional<PartyRoom> partyRoomDb = this.partyRoomRepository.findById(partyRoomId);
        if(partyRoomDb.isPresent()){
            this.partyRoomRepository.deleteById(partyRoomId);
            return partyRoomDb.get();
        }
        else{
            throw new ResourceNotFoundException("Record not found with id : " + partyRoomId);
        }
    }
}
