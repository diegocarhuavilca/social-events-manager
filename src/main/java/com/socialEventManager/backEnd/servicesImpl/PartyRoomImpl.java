package com.socialEventManager.backEnd.servicesImpl;

import com.socialEventManager.backEnd.dto.ExtraPartyRoomDTO;
import com.socialEventManager.backEnd.dto.PartyRoomDTO;
import com.socialEventManager.backEnd.dto.UpdatePartyRoomStatusDTO;
import com.socialEventManager.backEnd.helpers.PartyRoomValidator;
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

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Transactional
public class PartyRoomImpl implements PartyRoomService {

    @Autowired
    private PartyRoomRepository partyRoomRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FirebaseService firebaseService;

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
    public PartyRoom createPartyRoom(PartyRoomDTO partyRoomDTO) throws IOException, InterruptedException, ExecutionException {

        PartyRoomValidator.allKeysNotEmptyValidator(partyRoomDTO);

        String mainPhotoURL = firebaseService.uploadImage(partyRoomDTO.getMainPhoto(), "social-events/"+partyRoomDTO.getName()+"/main_photo").get();

        List<String> secondaryPhotoURLs = partyRoomDTO.getSecondaryPhotos().stream()
                .map(file -> {
                    try {
                        return firebaseService.uploadImage(file, "social-events/"+partyRoomDTO.getName()+"/secondary_photos").get();
                    } catch (Exception e) {
                        throw new RuntimeException("Error uploading photo: " + file.getOriginalFilename(), e);
                    }
                })
                .toList();

        PartyRoom socialEventType = new PartyRoom(
                partyRoomDTO.getName(),
                partyRoomDTO.getDescription(),
                partyRoomDTO.getAddress(),
                partyRoomDTO.getDistrict(),
                mainPhotoURL,
                secondaryPhotoURLs,
                null,
                true
        );

        return this.partyRoomRepository.save(socialEventType);
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
    public List<ExtraPartyRoom> addExtraPartyRoom(String partyRoomId, ExtraPartyRoomDTO extraPartyRoomDTO) throws IOException, InterruptedException, ExecutionException {
        PartyRoom partyRoom = this.getPartyRoomById(partyRoomId);

        String mainPhotoURL = firebaseService.uploadImage(extraPartyRoomDTO.getMainPhoto(), "social-events/"+partyRoom.getName().replaceAll("\\s","-")+"/extras/mainPhoto").get();

        List<String> secondaryPhotoURLs = extraPartyRoomDTO.getSecondaryPhotos().stream()
                .map(file -> {
                    try {
                        return firebaseService.uploadImage(file, "social-events/"+extraPartyRoomDTO.getName().replaceAll("\\s","-")+"/extras/secondary_photos").get();
                    } catch (Exception e) {
                        throw new RuntimeException("Error uploading photo: " + file.getOriginalFilename(), e);
                    }
                })
                .toList();

        ObjectId objectId = new ObjectId();
        ExtraPartyRoom extraParty = new ExtraPartyRoom(objectId.toString(),extraPartyRoomDTO.getName(),extraPartyRoomDTO.getDescription(),mainPhotoURL,secondaryPhotoURLs);
        Query query = new Query().addCriteria(Criteria.where("_id").is(partyRoomId));
        Update addExtra = new Update().addToSet("extras",extraParty);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        return Objects.requireNonNull(this.mongoTemplate.findAndModify(query, addExtra, options, PartyRoom.class)).getExtras();
    }

    @Override
    public PartyRoom updateStatus(String partyRoomId, UpdatePartyRoomStatusDTO partyRoomStatus) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(partyRoomId));
        Update updateStatus = new Update().set("active",partyRoomStatus.getActive());
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        return Objects.requireNonNull(this.mongoTemplate.findAndModify(query,updateStatus,options, PartyRoom.class));
    }
}
