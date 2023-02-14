package net.chatfilter.chatfilterbackend;

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
