package net.chatfilter.chatfilterbackend.persistence.entity;

import java.util.UUID;

public abstract class Key {

    private String key;

    public Key(UUID baseUUID) {
        this.key = baseUUID.toString() + "." + UUID.randomUUID();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UUID getBaseUUID() {
        return UUID.fromString(key.split("\\.")[0]);
    }
}
