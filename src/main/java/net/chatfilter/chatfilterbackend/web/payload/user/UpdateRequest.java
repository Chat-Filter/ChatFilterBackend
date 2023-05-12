package net.chatfilter.chatfilterbackend.web.payload.user;

import net.chatfilter.chatfilterbackend.domain.dto.UserDTO;

public class UpdateRequest {

    private String key;
    private UserDTO userDTO;

    public UpdateRequest(String key, UserDTO userDTO) {
        this.key = key;
        this.userDTO = userDTO;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
