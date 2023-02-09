package net.chatfilter.chatfilterbackend.web.controller.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.Organization;
import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;
import net.chatfilter.chatfilterbackend.persistence.service.organization.OrganizationService;
import net.chatfilter.chatfilterbackend.persistence.service.user.UserService;
import net.chatfilter.chatfilterbackend.web.security.user.UserSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserSecurityManager userSecurityManager;

    @GetMapping("/get")
    public ResponseEntity<Organization> getOrganization(UserKey key, UUID organization) {
        if (!userSecurityManager.containsUser(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (!user.getOrganizations().contains(organization)) {
            return ResponseEntity.status(403).build();
        }

        // TODO: Hide statistics
        return ResponseEntity.ok(organizationService.getByUUID(organization));
    }

    @PostMapping
}
