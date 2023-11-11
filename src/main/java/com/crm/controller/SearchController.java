package com.crm.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.crm.dao.ContactRepository;
import com.crm.dao.UserRepository;
import com.crm.entities.CUser;
import com.crm.entities.Contact;


@RestController
public class SearchController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	
//	Searching name by Dynamic Query
	@GetMapping("/search/{q}")
	public ResponseEntity<?> search(@PathVariable("q") String q, Principal principal){
		CUser cuser = this.userRepository.getUserByUserName(principal.getName());
		List<Contact> contacts	=this.contactRepository.findByNameContainingAndUser(q, cuser);
		return ResponseEntity.ok(contacts);
//		return ResponseEntity.ok(null);
	}
}
