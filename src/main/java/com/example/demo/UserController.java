package com.example.demo;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserRepo userRepo;
	
	@GetMapping("/saveUser")
	@ResponseBody
	public User save(@AuthenticationPrincipal OAuth2User userPrincipal) {
		String userDetails="UserPrincpal Object: "+userPrincipal.toString()+" All Attributes: "+userPrincipal.getAttributes().toString();
		User user=new User();
		user.setId(UUID.randomUUID().toString());
		user.setUserDetails(userDetails);
		return userRepo.save(user);
	}
	
	@GetMapping("/getUser")
	@ResponseBody
	public List<User> getSavedUser() {
		return userRepo.findAll();
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/success")
	@ResponseBody
	public String success() {
		return "Successfully Authenticated";
	}
}
