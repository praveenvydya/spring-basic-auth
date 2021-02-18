package com.aws.vydya.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/user")
public class UserController {
	 
	
	@RequestMapping("/login")
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");
		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");
		return "login";
	}
	
	@GetMapping("/form")
	public String userForm(Model model) {
		model.addAttribute("isNew", true);
		model.addAttribute("userForm", new UserLogin());
		//model.addAttribute("roles", userService.roleList());
		return "user/form";
	}
//	 /, produces = MediaType.APPLICATION_JSON_VALUE
	@RequestMapping(value = "/ssologin", method = RequestMethod.GET)
	 public @ResponseBody ResponseEntity<?> searchUsers() {
	    	 
		ResponseEntity<?> resp = null;
		 HttpHeaders respHeaders = new HttpHeaders();
		 MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
			//requestBody.add("code", "rDfVwAXgIRSMJ4mF_rQj9LU5ROZyqM6X7JBzvAlfTZA");
			requestBody.add("username","imran");
			requestBody.add("password","Password1");
			 
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			//headers.add("Accept", "application/json");
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
//			  
			String authResp = null;
			
			//String resp;
			try {
				 resp = restTemplate.exchange("http://localhost:8090/user/login", HttpMethod.POST, formEntity,  String.class);
				//resp = restTemplate.postForObject("http://localhost:8080/api/v1/validateToken", entity, String.class);
//				if (null != resp) {//&& null != resp.getBody()
//					authResp = resp.getBody().toString();
//				}
			} catch (Exception e) {
				System.out.println("Exception"+e.getMessage());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(authResp);
				// logger.error("HttpStatusCodeException occurred while retriving Access_token
				// and Id_token for Authcode " + e);
			}
			respHeaders = resp.getHeaders();
			 
			 return ResponseEntity.ok()
			            .headers(respHeaders)
			            .body("Hello");
			 	//return ResponseEntity.status(resp.getStatusCode()).headers(respHeaders).body("hello");
	  	}
	
	  @RequestMapping(value = "/check", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	  	public @ResponseBody ResponseEntity<?> check(HttpServletRequest req,
	  			HttpServletRequest request, SessionStatus status) {
	    	 
	    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    		System.out.println(auth.getName());
	    		System.out.println(auth.isAuthenticated());
			 	return ResponseEntity.status(HttpStatus.OK).body(auth.isAuthenticated()+ " : hello : "+auth.getName());
	  	}
}
