package com.socialEventManager.backEnd.repositories;

import com.socialEventManager.backEnd.models.PartyRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartyRoomRepository extends MongoRepository<PartyRoom,String> {
}
