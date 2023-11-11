package com.crm.controller;



import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.dao.BasicEnquiryRepository;
import com.crm.dao.UserRepository;
import com.crm.entities.BasicEnquiry;
import com.crm.entities.CUser;
import com.crm.helper.Message;


@Controller
public class HomeController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BasicEnquiryRepository basicEnquiryRepositor;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("titile","Home -Smart Contatct Manager");
		return "home";
	}
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("titile","About -Smart Contatct Manager");
		return "about";
	}
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("titile","Register -Smart Contatct Manager");
		model.addAttribute("user",new CUser());
		return "signup";
	}
//	
//	@PostMapping("/signin")
//	public String login(Model model) {
//		model.addAttribute("titile","Login -Smart Contatct Manager");
//		return "login";
//	}
// This handler is for new Login
	@GetMapping("/signin")
	public String signin(Model model) {
		model.addAttribute("titile","Login - CRM");
		return "login";
	}
	
//	this Handler is for Register the user
	@RequestMapping(value="/do_register",method=RequestMethod.POST)
	public String registerUser(@javax.validation.Valid @ModelAttribute("user") CUser user,BindingResult result1,@RequestParam(value="agreement", defaultValue = "false" )
	boolean agreement, Model m, HttpSession session) {
		try {
			if(!agreement) {
				System.out.println("you have not agreed the Terms & Condition");
				throw new Exception();
			}
			if(result1.hasErrors()) {
				System.out.println("ERROR"+result1.toString());
				m.addAttribute("user",user);
				return "signup";
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			System.out.println("Agreement:"+ agreement);
			System.out.println("User:"+ user);
			
			CUser result = userRepository.save(user);
			
			m.addAttribute("user", new CUser());
			session.setAttribute("message",new Message("Successfuly Registered!!","alert-success"));
			return "signup";
			
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("user",user);
			m.addAttribute("message",new Message("Something went wrong ! "+e.getMessage(),"alert-danger"));
			return "signup";
		}
	}
// This is Handler is for Register Enquiry	
	@RequestMapping("/enquiry")
	public String enquiryForm(Model basicEnquiry) {
		
		return "enquiry";
	}
	
	@RequestMapping(value= "/do_enquiry", method=RequestMethod.POST)
	public String enquiryRegister(@ModelAttribute("basicEnquiry") BasicEnquiry basicEnquiry, Model m, HttpSession session ){
		
		try {
//			System.out.println("Enquiry is: "+ basicEnquiry);
			basicEnquiry.setCreateOn(new Date());
			basicEnquiryRepositor.save(basicEnquiry);
			session.setAttribute("message", new Message ("Enquiry Submit Successful !","alert-success"));
			
			return "enquiry";
		} catch (Exception e) {
			m.addAttribute("message", new Message ("Something went wrong !"+e.getMessage(),"alert-danger"));
			e.printStackTrace();
		}
		return "enquiry";
	}
	
}
