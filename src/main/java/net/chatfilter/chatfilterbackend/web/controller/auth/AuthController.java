package net.chatfilter.chatfilterbackend.web.controller.auth;

import net.chatfilter.chatfilterbackend.domain.dto.UserDTO;
import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import net.chatfilter.chatfilterbackend.persistence.mapper.UserMapper;
import net.chatfilter.chatfilterbackend.persistence.service.user.UserAuthResult;
import net.chatfilter.chatfilterbackend.persistence.service.user.UserService;
import net.chatfilter.chatfilterbackend.web.security.user.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(String email, String password) {
        UserAuthResult result = userService.auth(email, password);

        switch (result) {
            case SUCCESS -> {
                return ResponseEntity.ok(userMapper.toUserDTO(userService.getByEmail(email)));
            }
            case UNKNOWN_USER, WRONG_PASSWORD -> {
                return ResponseEntity.status(401).build();
            }
        }

        return ResponseEntity.status(500).build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(String email, String password, String name, String lastName) {
        if (userService.getByEmail(email) != null) {
            return ResponseEntity.status(409).build();
        }

        UUID userUUID = UUID.randomUUID();
        while (userService.getByUUID(userUUID) != null) {
            userUUID = UUID.randomUUID();
        }

        password = authUtil.encodePassword(password);
        User user = new User(userUUID, email, password, name, lastName);
        UserDTO userDTO = userMapper.toUserDTO(userService.create(user));
        return ResponseEntity.ok(userDTO);
    }
}
