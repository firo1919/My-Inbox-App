package com.firomsa.MyInboxApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MyInboxApp {

	public static void main(String[] args) {
		SpringApplication.run(MyInboxApp.class, args);
	}
	@GetMapping("/user")
	public String getUserInfo(@AuthenticationPrincipal OAuth2User user){
		return user.getAttribute("name");
	}

}