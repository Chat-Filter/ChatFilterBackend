package net.chatfilter.chatfilterbackend;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.role.OrganizationPermission;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.role.OrganizationRole;
import net.chatfilter.chatfilterbackend.persistence.service.organization.role.OrganizationRoleService;
import net.chatfilter.chatfilterbackend.persistence.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class ChatFilterBackendApplication {

	private static String DEFAULT_ROLE;

	public static void main(String[] args) {
		SpringApplication.run(ChatFilterBackendApplication.class, args);
	}

	public static String getDefaultRole() {
		return DEFAULT_ROLE;
	}
}
