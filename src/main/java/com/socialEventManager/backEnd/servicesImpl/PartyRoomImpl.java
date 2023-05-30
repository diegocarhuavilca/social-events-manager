package com.socialEventManager.backEnd.servicesImpl;

import com.socialEventManager.backEnd.models.ExtraPartyRoom;
import com.socialEventManager.backEnd.models.PartyRoom;
import com.socialEventManager.backEnd.models.SocialEventType;
import com.socialEventManager.backEnd.repositories.PartyRoomRepository;
import com.socialEventManager.backEnd.repositories.SocialEventTypeRepository;
import com.socialEventManager.backEnd.services.PartyRoomService;
import com.socialEventManager.backEnd.services.SocialEventTypeService;
import com.socialEventManager.backEnd.utils.ResourceNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PartyRoomImpl implements PartyRoomService {

    @Autowired
    private PartyRoomRepository partyRoomRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

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

    @Override
    public List<ExtraPartyRoom> getAllExtrasPartyRoomById(String partyRoomId) {
        PartyRoom partyDb = this.partyRoomRepository.findAllExtrasOfPartyRoomById(partyRoomId);
        if(partyDb == null){
            throw new ResourceNotFoundException("Record not found with id : " + partyRoomId);
        }else{
            return partyDb.getExtras();
        }
    }

    @Override
    public ExtraPartyRoom getExtraPartyRoomByPartyRoomId(String partyRoomId, String extraPartyRoomId) {
        List<ExtraPartyRoom> extrasPartyRoom = this.getAllExtrasPartyRoomById(partyRoomId);
        ExtraPartyRoom extraPartyRoomFiltered = extrasPartyRoom.stream().filter(extraPartyRoom ->
                (extraPartyRoom.getId().equals(extraPartyRoomId))
        ).findFirst().orElse(null);
        if (extraPartyRoomFiltered == null){
            throw new ResourceNotFoundException("Record not found with id : " + partyRoomId);
        }else{
            return extraPartyRoomFiltered;
        }
    }

    @Override
    public List<ExtraPartyRoom> addExtraPartyRoom(String partyRoomId, ExtraPartyRoom extraPartyRoom) {
        ObjectId objectId = new ObjectId();
        ExtraPartyRoom extraParty = new ExtraPartyRoom(objectId.toString(),extraPartyRoom.getName(),extraPartyRoom.getDescription(),extraPartyRoom.getMainPhoto(),extraPartyRoom.getSecondaryPhotos());
        Query query = new Query().addCriteria(Criteria.where("_id").is(partyRoomId));
        Update addExtra = new Update().addToSet("extras",extraParty);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        return this.mongoTemplate.findAndModify(query,addExtra,options, PartyRoom.class).getExtras();
    }
}
