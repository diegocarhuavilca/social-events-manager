package com.socialEventManager.backEnd.services;

import com.socialEventManager.backEnd.models.SocialEventType;


import java.util.List;

public interface SocialEventTypeService {
    List<SocialEventType> getAllSocialEventType();
    SocialEventType getSocialEventTypeById(String id);
    SocialEventType createSocialEventType(SocialEventType socialEventType);
    SocialEventType deleteSocialEventTypeById(String id);
}
