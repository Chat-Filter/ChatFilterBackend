package net.chatfilter.chatfilterbackend.web.controller.user;

import net.chatfilter.chatfilterbackend.domain.dto.UserDTO;
import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;
import net.chatfilter.chatfilterbackend.persistence.mapper.UserMapper;
import net.chatfilter.chatfilterbackend.persistence.service.user.UserService;
import net.chatfilter.chatfilterbackend.web.security.user.UserSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserSecurityManager userSecurityManager;

    @GetMapping("/get")
    public ResponseEntity<UserDTO> getUser(UserKey key) {
        if (!userSecurityManager.containsUser(key)) {
            return ResponseEntity.status(401).build();
        }

        UserDTO userDTO = userMapper.toUserDTO(userService.getByUUID(key.getBaseUUID()));
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<UserDTO> updateUser(UserKey key, UserDTO userDTO) {
        if (!userSecurityManager.containsUser(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        userService.update(user);

        return ResponseEntity.ok(userDTO);
    }
}
