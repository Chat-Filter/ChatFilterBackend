package net.chatfilter.chatfilterbackend.web.controller.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.Organization;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.role.OrganizationPermission;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.role.OrganizationRole;
import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;
import net.chatfilter.chatfilterbackend.persistence.service.organization.OrganizationService;
import net.chatfilter.chatfilterbackend.persistence.service.organization.role.OrganizationRoleService;
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
    private OrganizationRoleService organizationRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserSecurityManager userSecurityManager;

    @GetMapping("/get")
    public ResponseEntity<Organization> getOrganization(UserKey key, UUID uuid) {
        if (!userSecurityManager.containsUser(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (!user.getOrganizations().contains(uuid)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getByUUID(uuid);
        OrganizationRole role = organizationRoleService.get(organization.getMembers().get(key.getBaseUUID()).getRole());
        if (!role.hasPermission(OrganizationPermission.SEE_STATISTICS)) {
            // TODO: Hide statistics
        }

        return ResponseEntity.ok(organization);
    }

    @PostMapping("/update/name")
    public ResponseEntity<Organization> updateName(UserKey key, UUID uuid, String name) {
        if (!userSecurityManager.containsUser(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (!user.getOrganizations().contains(uuid)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getByUUID(uuid);
        OrganizationRole role = organizationRoleService.get(organization.getMembers().get(key.getBaseUUID()).getRole());
        if (!role.hasPermission(OrganizationPermission.UPDATE_NAME)) {
            return ResponseEntity.status(403).build();
        }

        organization.setName(name);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @PostMapping("/update/invite-member")

}
