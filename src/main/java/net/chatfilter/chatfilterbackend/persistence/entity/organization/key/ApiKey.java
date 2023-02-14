package net.chatfilter.chatfilterbackend.persistence.entity.organization.key;

import net.chatfilter.chatfilterbackend.persistence.entity.Key;

import java.util.UUID;

public class ApiKey extends Key {

    public ApiKey(String organizationId) {
        super(organizationId);
    }
}
