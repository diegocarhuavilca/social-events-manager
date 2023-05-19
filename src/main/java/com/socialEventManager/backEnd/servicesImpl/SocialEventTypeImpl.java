package com.socialEventManager.backEnd.servicesImpl;

import com.socialEventManager.backEnd.models.SocialEventType;
import com.socialEventManager.backEnd.repositories.SocialEventTypeRepository;
import com.socialEventManager.backEnd.services.SocialEventTypeService;
import com.socialEventManager.backEnd.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SocialEventTypeImpl implements SocialEventTypeService {

    @Autowired
    private SocialEventTypeRepository socialEventTypeRepository;

    @Override
    public List<SocialEventType> getAllSocialEventType () {
        return this.socialEventTypeRepository.findAll();
    }

    @Override
    public SocialEventType getSocialEventTypeById (String socialEventTypeId) {
        Optional < SocialEventType > SocialEventTypeDb = this.socialEventTypeRepository.findById(socialEventTypeId);

        if (SocialEventTypeDb.isPresent()) {
            return SocialEventTypeDb.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + socialEventTypeId);
        }
    }

    @Override
    public SocialEventType createSocialEventType(SocialEventType socialEventType){
        return this.socialEventTypeRepository.save(socialEventType);
    }

    @Override
    public SocialEventType deleteSocialEventTypeById(String socialEventTypeId){
        Optional<SocialEventType> socialEventFound = this.socialEventTypeRepository.findById(socialEventTypeId);
        if(socialEventFound.isPresent()){
            this.socialEventTypeRepository.deleteById(socialEventTypeId);
            return socialEventFound.get();
        }
        else{
            throw new ResourceNotFoundException("Record not found with id : " + socialEventTypeId);
        }
    }
}
