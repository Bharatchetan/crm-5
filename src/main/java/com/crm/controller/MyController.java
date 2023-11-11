package com.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.dao.UserRepository;
import com.crm.entities.CUser;
import com.crm.entities.Contact;

@Controller
public class MyController {
	@Autowired
	private UserRepository userRepository;
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		CUser user=new CUser();
		user.setName("Chetan");
		user.setEmail("Chetan@enterprise.com");
		Contact contact=new Contact();
		user.getContacts().add(contact);
		userRepository.save(user);
		return "working";
	}
}
