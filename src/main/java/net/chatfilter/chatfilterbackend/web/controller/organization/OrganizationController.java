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
import net.chatfilter.chatfilterbackend.web.payload.organization.*;
import net.chatfilter.chatfilterbackend.web.security.user.UserSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<Organization> getOrganization(@RequestBody GetRequest request) {
        UserKey key = request.getKey();
        String organizationId = request.getOrganizationId();

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (!user.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getById(organizationId);
        OrganizationRole role = organizationRoleService.getById(organization.getMembers().get(key.getBaseId()).getRole());
        if (!role.hasPermission(OrganizationPermission.SEE_STATISTICS)) {
            organization.setChecks(null);
        }

        return ResponseEntity.ok(organization);
    }

    @PostMapping("/update/name")
    public ResponseEntity<Organization> updateName(@RequestBody UpdateNameRequest request) {
        UserKey key = request.getKey();
        String organizationId = request.getOrganizationId();
        String name = request.getName();

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (!user.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getById(organizationId);
        OrganizationRole role = organizationRoleService.getById(organization.getMembers().get(key.getBaseId()).getRole());
        if (!role.hasPermission(OrganizationPermission.UPDATE_NAME)) {
            return ResponseEntity.status(403).build();
        }

        organization.setName(name);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @PostMapping("/invite-member")
    public ResponseEntity<Organization> inviteMember(@RequestBody InviteRequest request) {
        UserKey key = request.getKey();
        String organizationId = request.getOrganizationId();
        String invitedEmail = request.getInvitedEmail();

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (!user.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getById(organizationId);
        OrganizationRole role = organizationRoleService.getById(organization.getMembers().get(key.getBaseId()).getRole());
        if (!role.hasPermission(OrganizationPermission.INVITE_MEMBER)) {
            return ResponseEntity.status(403).build();
        }

        User invited = userService.getByEmail(invitedEmail);
        if (invited == null) {
            return ResponseEntity.status(404).build();
        }

        if (invited.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(409).build();
        }

        invited.getPendingOrganizationInvites().add(organizationId);
        organization.getPendingInvites().add(invited.getId());
        userService.update(invited);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @PostMapping("/update/delete-invite")
    public ResponseEntity<Organization> deleteInvite(@RequestBody InviteRequest request) {
        UserKey key = request.getKey();
        String organizationId = request.getOrganizationId();
        String invitedId = request.getInvitedEmail();

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (!user.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getById(organizationId);
        OrganizationRole role = organizationRoleService.getById(organization.getMembers().get(key.getBaseId()).getRole());
        if (!role.hasPermission(OrganizationPermission.DELETE_INVITE)) {
            return ResponseEntity.status(403).build();
        }

        User invited = userService.getById(invitedId);
        if (invited == null) {
            return ResponseEntity.status(404).build();
        }

        invited.getPendingOrganizationInvites().remove(organizationId);
        organization.getPendingInvites().remove(invitedId);
        userService.update(invited);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @PostMapping("/join")
    public ResponseEntity<Organization> join(@RequestBody JoinAndLeaveRequest request) {
        UserKey key = request.getKey();
        String organizationId = request.getOrganizationId();

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (!user.getPendingOrganizationInvites().contains(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getById(organizationId);
        if (organization == null || !organization.getPendingInvites().contains(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        OrganizationMember member = new OrganizationMember(user.getId(), ChatFilterBackendApplication.getDefaultRole());
        organization.getMembers().put(user.getId(), member);
        user.getOrganizations().add(organizationId);
        user.getPendingOrganizationInvites().remove(organizationId);
        organization.getPendingInvites().remove(user);

        userService.update(user);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @PostMapping("/leave")
    public ResponseEntity<UserDTO> leave(@RequestBody JoinAndLeaveRequest request) {
        UserKey key = request.getKey();
        String organizationId = request.getOrganizationId();

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (!user.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getById(organizationId);
        if (organization == null) {
            return ResponseEntity.status(403).build();
        }

        organization.getMembers().remove(user.getId());
        user.getOrganizations().remove(organizationId);

        organization = organizationService.update(organization);
        userService.update(user);
        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    @PostMapping("/create")
    public ResponseEntity<Organization> create(@RequestBody CreateRequest request) {
        UserKey key = request.getKey();
        String name = request.getName();

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (user == null) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = new Organization(user.getId(), name);
        user.getOrganizations().add(organization.getId());

        organization = organizationService.create(organization);
        user = userService.update(user);

        return ResponseEntity.ok(organization);
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody DeleteRequest request) {
        UserKey key = request.getKey();
        String organizationId = request.getOrganizationId();

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (!user.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getById(organizationId);
        if (!organization.getOwner().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        for (OrganizationMember member : organization.getMembers().values()) {
            User memberUser = userService.getById(member.getId());
            memberUser.getOrganizations().remove(organizationId);
            userService.update(memberUser);
        }

        organizationService.delete(organizationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/user-role")
    public ResponseEntity<Organization> updateRole(@RequestBody UpdateRoleRequest request) {
        UserKey key = request.getKey();
        String organizationId = request.getOrganizationId();
        String userToUpdate = request.getUserToUpdate();
        String roleName = request.getRole();

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (!user.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getById(organizationId);
        if (!organization.getOwner().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        OrganizationMember updatedMember = organization.getMembers().get(userToUpdate);
        if (updatedMember == null) {
            return ResponseEntity.status(404).build();
        }

        OrganizationRole role = organizationRoleService.getByName(roleName);
        if (role == null) {
            return ResponseEntity.status(404).build();
        }

        updatedMember.setRole(role.getId());
        organization = organizationService.update(organization);

        return ResponseEntity.ok(organization);
    }

    @PostMapping("/kick")
    public ResponseEntity<Organization> kickFromOrganization(@RequestBody KickRequest request) {
        UserKey key = request.getKey();
        String organizationId = request.getOrganizationId();
        String userToKick = request.getUserToKick();

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (!user.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getById(organizationId);
        if (!organization.getOwner().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        User kickedUser = userService.getById(userToKick);
        if (kickedUser == null) {
            return ResponseEntity.status(404).build();
        }

        if (!kickedUser.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(404).build();
        }

        kickedUser.getOrganizations().remove(organizationId);
        organization.getMembers().remove(userToKick);

        userService.update(kickedUser);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

}
