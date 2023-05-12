package net.chatfilter.chatfilterbackend.web.controller.organization;

import net.chatfilter.chatfilterbackend.domain.dto.UserDTO;
import net.chatfilter.chatfilterbackend.persistence.entity.Key;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.Organization;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.invite.InvitedUser;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.member.MemberData;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.member.OrganizationMember;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.role.OrganizationPermission;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.role.OrganizationRole;
import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import net.chatfilter.chatfilterbackend.persistence.entity.user.invite.PendingInvite;
import net.chatfilter.chatfilterbackend.persistence.mapper.UserMapper;
import net.chatfilter.chatfilterbackend.persistence.service.organization.OrganizationService;
import net.chatfilter.chatfilterbackend.persistence.service.organization.role.OrganizationRoleService;
import net.chatfilter.chatfilterbackend.persistence.service.user.UserService;
import net.chatfilter.chatfilterbackend.web.payload.organization.*;
import net.chatfilter.chatfilterbackend.web.security.user.UserSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
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

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public ResponseEntity<Organization> getOrganization(String userKey, String organizationId) {
        Key key = new Key(userKey);

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (!user.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getById(organizationId);
        if (organization == null) return ResponseEntity.status(404).build();

        OrganizationRole role = organizationRoleService.getById(organization.getMembers().get(key.getBaseId()).getRole());
        if (!role.hasPermission(OrganizationPermission.SEE_STATISTICS)) {
            organization.setChecks(null);
        }

        return ResponseEntity.ok(organization);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/update/name")
    public ResponseEntity<Organization> updateName(@RequestBody UpdateNameRequest request) {
        Key key = request.getKey();
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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/invite-member")
    public ResponseEntity<Organization> inviteMember(@RequestBody InviteRequest request) {
        Key key = new Key(request.getKey());
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

        PendingInvite pendingInvite = new PendingInvite(organizationId, organization.getName());
        invited.getPendingOrganizationInvites().put(organizationId, pendingInvite);
        InvitedUser invitedUser = new InvitedUser(organizationId, invitedEmail);
        organization.getPendingInvites().put(invitedEmail, invitedUser);
        userService.update(invited);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/update/delete-invite")
    public ResponseEntity<Organization> deleteInvite(@RequestBody InviteRequest request) {
        Key key = new Key(request.getKey());
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

        User invited = userService.getByEmail(invitedId);
        if (invited == null) {
            return ResponseEntity.status(404).build();
        }

        invited.getPendingOrganizationInvites().remove(organizationId);
        organization.getPendingInvites().remove(invitedId);
        userService.update(invited);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/join")
    public ResponseEntity<Organization> join(@RequestBody JoinAndLeaveRequest request) {
        Key key = request.getKey();
        String organizationId = request.getOrganizationId();

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (!user.getPendingOrganizationInvites().containsKey(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getById(organizationId);
        if (organization == null || !organization.getPendingInvites().containsKey(user.getEmail())) {
            return ResponseEntity.status(403).build();
        }

        OrganizationMember member = new OrganizationMember(user.getId(), organizationRoleService.getByName("user").getId());
        organization.getMembers().put(user.getId(), member);
        user.getOrganizations().add(organizationId);
        user.getPendingOrganizationInvites().remove(organizationId);
        organization.getPendingInvites().remove(user.getEmail());

        userService.update(user);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/leave")
    public ResponseEntity<UserDTO> leave(@RequestBody JoinAndLeaveRequest request) {
        Key key = request.getKey();
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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<Organization> create(@RequestBody CreateRequest request) {
        Key key = new Key(request.getKey());
        String name = request.getName();

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (user == null) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = new Organization(user.getId(), name);

        OrganizationRole ownerRole = organizationRoleService.getByName("owner");
        organization.getMembers().put(user.getId(), new OrganizationMember(user.getId(), ownerRole.getId()));

        organization = organizationService.create(organization);
        organization.setKey(new Key(organization.getId()));
        organization = organizationService.update(organization);

        user.getOrganizations().add(organization.getId());
        user = userService.update(user);

        return ResponseEntity.ok(organization);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody DeleteRequest request) {
        Key key = request.getKey();
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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/update/user-role")
    public ResponseEntity<Organization> updateRole(@RequestBody UpdateRoleRequest request) {
        Key key = request.getKey();
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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/kick")
    public ResponseEntity<Organization> kickFromOrganization(@RequestBody KickRequest request) {
        Key key = new Key(request.getKey());
        String organizationId = request.getOrganizationId();
        String userToKick = request.getUserIdToKick();

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

        OrganizationRole role = organizationRoleService.getById(organization.getMembers().get(key.getBaseId()).getRole());
        if (!role.hasPermission(OrganizationPermission.KICK_MEMBERS)) {
            return ResponseEntity.status(403).build();
        }

        if (organization.getOwner().equals(userToKick)) {
            return ResponseEntity.status(403).build();
        }

        if (key.getBaseId().equals(userToKick)) {
            return ResponseEntity.status(403).build();
        }

        User target = userService.getById(userToKick);
        if (target == null) {
            return ResponseEntity.status(404).build();
        }

        if (!target.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(404).build();
        }

        target.getOrganizations().remove(organizationId);
        organization.getMembers().remove(userToKick);

        userService.update(target);
        organization = organizationService.update(organization);
        return ResponseEntity.ok(organization);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/member-data")
    public ResponseEntity<MemberData> getMemberData(String userKey, String organizationId, String memberId) {
        Key key = new Key(userKey);

        if (!userSecurityManager.isValid(key)) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getById(key.getBaseId());
        if (!user.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        User target = userService.getById(memberId);
        if (!target.getOrganizations().contains(organizationId)) {
            return ResponseEntity.status(403).build();
        }

        Organization organization = organizationService.getById(organizationId);

        String role = organizationRoleService.getById(organization.getMembers().get(memberId).getRole()).getName();
        return ResponseEntity.ok(new MemberData(target.getId(), target.getEmail(), target.getName(), target.getLastName(), role));
    }
}
