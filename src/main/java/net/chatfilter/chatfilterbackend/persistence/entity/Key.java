package net.chatfilter.chatfilterbackend.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.Nullable;

import java.util.UUID;

public class Key {

    private String key;

    public Key(String key) {
        if (key.contains(".")) {
            this.key = key;
        } else {
            this.key = key + "." + UUID.randomUUID();
        }
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
