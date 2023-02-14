package net.chatfilter.chatfilterbackend.web.controller.user;

import net.chatfilter.chatfilterbackend.domain.dto.UserDTO;
import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;
import net.chatfilter.chatfilterbackend.persistence.mapper.UserMapper;
import net.chatfilter.chatfilterbackend.persistence.service.user.UserService;
import net.chatfilter.chatfilterbackend.web.security.user.UserSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<User> getUser(@RequestBody UserKey key) {
        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        System.out.println("USER: " + user);
        System.out.println("BaseUUID: " + key.getBaseId());
        UserDTO userDTO = userMapper.toUserDTO(userService.getById(key.getBaseId()));
        return ResponseEntity.ok(userService.getById(key.getBaseId()));
    }

    @PostMapping("/update")
    public ResponseEntity<UserDTO> updateUser(UserKey key, UserDTO userDTO) {
        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user = userService.update(user);

        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }
}
