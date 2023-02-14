package net.chatfilter.chatfilterbackend.web.controller.auth;

import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;
import net.chatfilter.chatfilterbackend.persistence.mapper.UserMapper;
import net.chatfilter.chatfilterbackend.persistence.service.user.UserAuthResult;
import net.chatfilter.chatfilterbackend.persistence.service.user.UserService;
import net.chatfilter.chatfilterbackend.web.security.user.UserAuthUtil;
import net.chatfilter.chatfilterbackend.web.security.user.UserSecurityManager;
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
    private UserAuthUtil userAuthUtil;
    @Autowired
    private UserSecurityManager userSecurityManager;

    @PostMapping("/login")
    public ResponseEntity<UserKey> login(String email, String password) {
        UserAuthResult result = userService.auth(email, password);

        switch (result) {
            case SUCCESS -> {
                User user = userService.getByEmail(email);
                UserKey userKey = new UserKey(user.getId());
                userSecurityManager.insertUser(userKey);
                return ResponseEntity.ok(userKey);
            }
            case UNKNOWN_USER, WRONG_PASSWORD -> {
                return ResponseEntity.status(401).build();
            }
        }

        return ResponseEntity.status(500).build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserKey> register(String email, String password, String name, String lastName) {
        if (userService.getByEmail(email) != null) {
            return ResponseEntity.status(409).build();
        }

        password = userAuthUtil.encodePassword(password);
        User user = new User(email, password, name, lastName);
        userService.create(user);

        UserKey userKey = new UserKey(user.getId());
        userSecurityManager.insertUser(userKey);
        return ResponseEntity.ok(userKey);
    }
}
