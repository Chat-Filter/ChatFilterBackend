package net.chatfilter.chatfilterbackend.web.controller.auth;

import net.chatfilter.chatfilterbackend.persistence.entity.Key;
import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import net.chatfilter.chatfilterbackend.persistence.mapper.UserMapper;
import net.chatfilter.chatfilterbackend.persistence.service.user.UserAuthResult;
import net.chatfilter.chatfilterbackend.persistence.service.user.UserService;
import net.chatfilter.chatfilterbackend.web.payload.auth.LoginRequest;
import net.chatfilter.chatfilterbackend.web.payload.auth.RegisterRequest;
import net.chatfilter.chatfilterbackend.web.security.user.UserAuthUtil;
import net.chatfilter.chatfilterbackend.web.security.user.UserSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<Key> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        UserAuthResult result = userService.auth(email, password);

        switch (result) {
            case SUCCESS -> {
                User user = userService.getByEmail(email);
                Key userKey = new Key(user.getId());
                userSecurityManager.insertUser(userKey);
                return ResponseEntity.ok(userKey);
            }
            case UNKNOWN_USER, WRONG_PASSWORD -> {
                return ResponseEntity.ok(null);
            }
        }

        return ResponseEntity.status(500).build();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/register")
    public ResponseEntity<Key> register(@RequestBody RegisterRequest request) {
        if (userService.getByEmail(request.getEmail()) != null) {
            return ResponseEntity.status(409).build();
        }

        request.setPassword(userAuthUtil.encodePassword(request.getPassword()));
        User user = new User(request.getEmail(), request.getPassword(), request.getName(), request.getLastName());
        userService.create(user);

        Key userKey = new Key(user.getId());
        userSecurityManager.insertUser(userKey);
        return ResponseEntity.ok(userKey);
    }
}
