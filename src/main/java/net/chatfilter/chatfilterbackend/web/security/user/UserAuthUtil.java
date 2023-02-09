package net.chatfilter.chatfilterbackend.web.security.user;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthUtil {
    private PasswordEncoder passwordEncoder;

    public UserAuthUtil() {
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public boolean passwordMatches(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
