package net.chatfilter.chatfilterbackend.web.payload.user;

import net.chatfilter.chatfilterbackend.domain.dto.UserDTO;
import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;

public class UpdateRequest {

    private UserKey key;
    private UserDTO userDTO;

    public UpdateRequest(UserKey key, UserDTO userDTO) {
        this.key = key;
        this.userDTO = userDTO;
    }

    public UserKey getKey() {
        return key;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setKey(UserKey key) {
        this.key = key;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
