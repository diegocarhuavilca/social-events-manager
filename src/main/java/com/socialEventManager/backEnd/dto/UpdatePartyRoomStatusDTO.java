package com.socialEventManager.backEnd.dto;

import lombok.Data;
@Data
public  class UpdatePartyRoomStatusDTO  {
    private Boolean active;
    private String name;

    public UpdatePartyRoomStatusDTO(Boolean active, String name) {
        this.active = active;
        this.name = name;
    }
}