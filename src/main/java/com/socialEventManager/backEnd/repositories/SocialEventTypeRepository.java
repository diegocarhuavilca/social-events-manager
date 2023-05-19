package com.socialEventManager.backEnd.repositories;

import com.socialEventManager.backEnd.models.SocialEventType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SocialEventTypeRepository extends MongoRepository <SocialEventType,String>{
}
