package com.socialEventManager.backEnd.services;

import com.socialEventManager.backEnd.dto.SocialEventTypeDTO;
import com.socialEventManager.backEnd.models.SocialEventType;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SocialEventTypeService {
    List<SocialEventType> getAllSocialEventType();
    SocialEventType getSocialEventTypeById(String id);
//    SocialEventType createSocialEventType(SocialEventType socialEventType);

    SocialEventType createSocialEventType(SocialEventTypeDTO socialEventTypeDTO) throws ExecutionException, IOException, InterruptedException;
    SocialEventType deleteSocialEventTypeById(String id);
}
