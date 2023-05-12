package net.chatfilter.chatfilterbackend.web.controller.user;

import net.chatfilter.chatfilterbackend.domain.dto.UserDTO;
import net.chatfilter.chatfilterbackend.persistence.entity.Key;
import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import net.chatfilter.chatfilterbackend.persistence.mapper.UserMapper;
import net.chatfilter.chatfilterbackend.persistence.service.user.UserService;
import net.chatfilter.chatfilterbackend.web.payload.user.UpdateRequest;
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

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public ResponseEntity<UserDTO> getUser(String key) {
        Key userKey = new Key(key);
        if (!userSecurityManager.isValid(userKey)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(userKey.getBaseId());
        UserDTO userDTO = userMapper.toUserDTO(userService.getById(userKey.getBaseId()));
        return ResponseEntity.ok(userDTO);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateRequest request) {
        Key key = new Key(request.getKey());
        UserDTO userDTO = request.getUserDTO();

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
