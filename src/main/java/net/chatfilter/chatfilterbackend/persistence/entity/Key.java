package net.chatfilter.chatfilterbackend.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public abstract class Key {

    private String key;

    public Key(String baseId) {
        this.key = baseId + "." + UUID.randomUUID();
    }

    public Key(String key, String baseId) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBaseId() {
        return key.split("\\.")[0];
    }

    @Override
    public boolean equals(Object obj) {
        Key objectKey = (Key) obj;
        return key.equals(objectKey.getKey());
    }
}
