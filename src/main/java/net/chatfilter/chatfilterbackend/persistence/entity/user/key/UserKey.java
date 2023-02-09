package net.chatfilter.chatfilterbackend.persistence.entity.user.key;

import net.chatfilter.chatfilterbackend.persistence.entity.Key;

import java.util.UUID;

public class UserKey extends Key {

    public UserKey(UUID userUUID) {
        super(userUUID);
    }
}
