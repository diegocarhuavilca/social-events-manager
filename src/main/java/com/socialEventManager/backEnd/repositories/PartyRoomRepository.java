package com.socialEventManager.backEnd.repositories;

import com.socialEventManager.backEnd.models.PartyRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PartyRoomRepository extends MongoRepository<PartyRoom,String>  {
    @Query(value = "{'id': ?0}", fields = "{'extras': 1}")
    PartyRoom findAllExtrasOfPartyRoomById(String partyRoomId);
}
