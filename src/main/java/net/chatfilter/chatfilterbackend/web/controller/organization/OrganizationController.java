package net.chatfilter.chatfilterbackend.web.controller.organization;

import net.chatfilter.chatfilterbackend.ChatFilterBackendApplication;
import net.chatfilter.chatfilterbackend.domain.dto.UserDTO;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.Organization;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.member.OrganizationMember;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.role.OrganizationPermission;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.role.OrganizationRole;
import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;
import net.chatfilter.chatfilterbackend.persistence.mapper.UserMapper;
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
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/get")
    public ResponseEntity<Organization> getOrganization(UserKey key, UUID organizationUUID) {
        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (!user.getOrganizations().contains(organizationUUID)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getByUUID(organizationUUID);
        OrganizationRole role = organizationRoleService.getByUUID(organization.getMembers().get(key.getBaseUUID()).getRole());
        if (!role.hasPermission(OrganizationPermission.SEE_STATISTICS)) {
            organization.setChecks(null);
        }

        return ResponseEntity.ok(organization);
    }

    @PostMapping("/update/name")
    public ResponseEntity<Organization> updateName(UserKey key, UUID organizationUUID, String name) {
        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (!user.getOrganizations().contains(organizationUUID)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getByUUID(organizationUUID);
        OrganizationRole role = organizationRoleService.getByUUID(organization.getMembers().get(key.getBaseUUID()).getRole());
        if (!role.hasPermission(OrganizationPermission.UPDATE_NAME)) {
            return ResponseEntity.status(403).build();
        }

        organization.setName(name);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @PostMapping("/invite-member")
    public ResponseEntity<Organization> inviteMember(UserKey key, UUID organizationUUID, String invitedEmail) {
        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (!user.getOrganizations().contains(organizationUUID)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getByUUID(organizationUUID);
        OrganizationRole role = organizationRoleService.getByUUID(organization.getMembers().get(key.getBaseUUID()).getRole());
        if (!role.hasPermission(OrganizationPermission.INVITE_MEMBER)) {
            return ResponseEntity.status(403).build();
        }

        User invited = userService.getByEmail(invitedEmail);
        if (invited == null) {
            return ResponseEntity.status(404).build();
        }

        if (invited.getOrganizations().contains(organizationUUID)) {
            return ResponseEntity.status(409).build();
        }

        invited.getPendingOrganizationInvites().add(organizationUUID);
        organization.getPendingInvites().add(invited.getUuid());
        userService.update(invited);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @PostMapping("/update/delete-invite")
    public ResponseEntity<Organization> deleteInvite(UserKey key, UUID organizationUUID, UUID invitedUUID) {
        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (!user.getOrganizations().contains(organizationUUID)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getByUUID(organizationUUID);
        OrganizationRole role = organizationRoleService.getByUUID(organization.getMembers().get(key.getBaseUUID()).getRole());
        if (!role.hasPermission(OrganizationPermission.DELETE_INVITE)) {
            return ResponseEntity.status(403).build();
        }

        User invited = userService.getByUUID(invitedUUID);
        if (invited == null) {
            return ResponseEntity.status(404).build();
        }

        invited.getPendingOrganizationInvites().remove(organizationUUID);
        organization.getPendingInvites().remove(invitedUUID);
        userService.update(invited);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @PostMapping("/join")
    public ResponseEntity<Organization> join(UserKey key, UUID organizationUUID) {
        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (!user.getPendingOrganizationInvites().contains(organizationUUID)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getByUUID(organizationUUID);
        if (organization == null || !organization.getPendingInvites().contains(user.getUuid())) {
            return ResponseEntity.status(403).build();
        }

        OrganizationMember member = new OrganizationMember(user.getUuid(), ChatFilterBackendApplication.getDefaultRole());
        organization.getMembers().put(user.getUuid(), member);
        user.getOrganizations().add(organizationUUID);
        user.getPendingOrganizationInvites().remove(organizationUUID);
        organization.getPendingInvites().remove(user);

        userService.update(user);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @PostMapping("/leave")
    public ResponseEntity<UserDTO> leave(UserKey key, UUID organizationUUID) {
        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (!user.getOrganizations().contains(organizationUUID)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getByUUID(organizationUUID);
        if (organization == null) {
            return ResponseEntity.status(403).build();
        }

        organization.getMembers().remove(user.getUuid());
        user.getOrganizations().remove(organizationUUID);

        organization = organizationService.update(organization);
        userService.update(user);
        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    @PostMapping("/create")
    public ResponseEntity<Organization> create(UserKey key, String name) {
        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        UUID organizationUUID = UUID.randomUUID();
        while (organizationService.getByUUID(organizationUUID) != null) {
            organizationUUID = UUID.randomUUID();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (user == null) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = new Organization(organizationUUID, user.getUuid(), name);
        user.getOrganizations().add(organization.getUuid());

        organization = organizationService.create(organization);
        user = userService.update(user);

        return ResponseEntity.ok(organization);
    }

    @PostMapping("/delete")
    public ResponseEntity delete(UserKey key, UUID organizationUUID) {
        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (!user.getOrganizations().contains(organizationUUID)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getByUUID(organizationUUID);
        if (!organization.getOwner().equals(user.getUuid())) {
            return ResponseEntity.status(403).build();
        }

        for (OrganizationMember member : organization.getMembers().values()) {
            User memberUser = userService.getByUUID(member.getUuid());
            memberUser.getOrganizations().remove(organizationUUID);
            userService.update(memberUser);
        }

        organizationService.delete(organizationUUID);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/user-role")
    public ResponseEntity<Organization> updateRole(UserKey key, UUID userToUpdateUUID, UUID organizationUUID, String roleName) {
        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (!user.getOrganizations().contains(organizationUUID)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getByUUID(organizationUUID);
        if (!organization.getOwner().equals(user.getUuid())) {
            return ResponseEntity.status(403).build();
        }

        OrganizationMember updatedMember = organization.getMembers().get(userToUpdateUUID);
        if (updatedMember == null) {
            return ResponseEntity.status(404).build();
        }

        OrganizationRole role = organizationRoleService.getByName(roleName);
        if (role == null) {
            return ResponseEntity.status(404).build();
        }

        updatedMember.setRole(role.getUuid());
        organization = organizationService.update(organization);

        return ResponseEntity.ok(organization);
    }

    @PostMapping("/kick")
    public ResponseEntity<Organization> kickFromOrganization(UserKey key, UUID userToKick, UUID organizationUUID) {
        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByUUID(key.getBaseUUID());
        if (!user.getOrganizations().contains(organizationUUID)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getByUUID(organizationUUID);
        if (!organization.getOwner().equals(user.getUuid())) {
            return ResponseEntity.status(403).build();
        }

        User kickedUser = userService.getByUUID(userToKick);
        if (kickedUser == null) {
            return ResponseEntity.status(404).build();
        }

        if (!kickedUser.getOrganizations().contains(organizationUUID)) {
            return ResponseEntity.status(404).build();
        }

        kickedUser.getOrganizations().remove(organizationUUID);
        organization.getMembers().remove(userToKick);

        userService.update(kickedUser);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

}
