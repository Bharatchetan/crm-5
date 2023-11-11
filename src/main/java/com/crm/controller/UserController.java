package com.crm.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.crm.dao.BasicEnquiryRepository;
import com.crm.dao.ContactRepository;
import com.crm.dao.ItemRepository;
import com.crm.dao.UserRepository;
import com.crm.entities.BasicEnquiry;
import com.crm.entities.CUser;
import com.crm.entities.Contact;
import com.crm.entities.ItemMaster;
import com.crm.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private BasicEnquiryRepository basicEnquiryRepository;
	@Autowired
	private ItemRepository itemRepository;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
//		System.out.println("UserName:"+userName);
//		get the user name using username
		CUser user = userRepository.getUserByUserName(userName);
//		System.out.println(user);
		model.addAttribute("user", user);
	}
/*
 * @RequestMapping("/index")
 *	public String dashboard(Model model) {
 *		model.addAttribute("title", "User Dashboard");
 *		return "normal/user_dashboard";
 *	}
 */
	@RequestMapping("/index")
	public String dashboard(Model model) {
		model.addAttribute("title", "User Dashboard");
		List<BasicEnquiry> basicEnquirys = this.basicEnquiryRepository.findAll();
		model.addAttribute("basicEnquirys",basicEnquirys);
		List<Contact> contacts = this.contactRepository.findAll();
		model.addAttribute("contacts",contacts);
		List<ItemMaster> items = this.itemRepository.findAll();
		model.addAttribute("items",items);
		return "normal/user_dashboard";
	}

	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "/normal/add_contact_form";
	}

//	Processing Add Contact Form
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Principal principal, HttpSession session) {
		try {
			String name = principal.getName();
			CUser user = this.userRepository.getUserByUserName(name);
			contact.setUser(user);
//Contact image	File Management	Processing & Uploading file..
			if (file.isEmpty()) {
//				Empty file message
//				System.out.println("File is Empty");
				contact.setImageUrl("contact.png");
			} else {
//				file update 
				contact.setImageUrl(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/images").getFile();
//				To get it unique append something in file name
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//				System.out.println("Image is Uploaded");
			}
			user.getContacts().add(contact);
			this.userRepository.save(user);
//			System.out.println(contact);
//			System.out.println("Added to Data base");
			session.setAttribute("message", new Message("Your Contact is added!!", "success"));
			return "normal/add_contact_form";
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went Wrong! Try Again!", "danger"));
		}
		return "normal/add_contact_form";
	}

//	show All Contacts Handler
//	per page =5[n]
	@GetMapping("/show_contact/{page}")
	public String showContact(@PathVariable("page") Integer page ,Model m, Principal principal) {
		m.addAttribute("title", "All Contacts");
		String userName=principal.getName();
		CUser user = this.userRepository.getUserByUserName(userName);
//		List of Contact use to check it is working.
//		List<Contact> contacts = this.contactRepository.findContactByCUser(user.getId());
		
//		currentPage-page
//		Contact per Page-5	
		Pageable pageable = PageRequest.of(page,5);
		
		Page<Contact> contacts = this.contactRepository.findContactByCUser(user.getId(),pageable);
		m.addAttribute("contacts",contacts);
//		System.out.println("Contacts"+contacts);
		m.addAttribute("currentPage",page);
		m.addAttribute("totalPages",contacts.getTotalPages());
		/*
		 * String userName=principal.getName();
		 * CUser user=this.userRepository.getUserByUserName(userName);
		 * List<Contact> contacts = user.getContacts();
		 * m.addAttribute("contacts",contacts);
		 */
		
		return "normal/show_contact";
	}
//	Showing Particular Contact Details;
	@RequestMapping("/{cid}/contact")
	public String showContactDetails(@PathVariable( "cid") Integer cId, Model m, Principal principal ){
//		System.out.println("CID"+ cId);
		Optional<Contact> contactOptional = this.contactRepository.findById(cId);
		Contact contact = contactOptional.get();
/*
 * USer Verification for Authorized access	
 */
		String userName = principal.getName();
		CUser authorizedUser = this.userRepository.getUserByUserName(userName);
		if(authorizedUser.getId()==contact.getUser().getId()) {
			m.addAttribute("contact",contact);
		}
		return "normal/contact_detail";
	}
	
//	Deleting Contact Handler
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cId, Model model,Principal principal, HttpSession session) {
//		Optional provide the ID Based Contact
//		Optional<Contact> contactOptional =this.contactRepository.findById(cId);
//		Contact contact =contactOptional.get();
		Contact contact = this.contactRepository.findById(cId).get();
		
//		DeleteById can also be Used	but venerable in case of Get Method by modifying the URL hence we will cross check contact is Authorized by User	
/*
 * USer Verification for Authorized access	
*/
				String userName = principal.getName();
				CUser authorizedUser = this.userRepository.getUserByUserName(userName);
				if(authorizedUser.getId()==contact.getUser().getId()) {
/* Entity Annotation Attribute orphanRemoval = true is used to Delete CascadeType.ALL impact. 
 * Although it is not require in N-tire Architecture because Services Annotation will handled that very well.
 *
 */	
//				contact.setUser(null); //No need it, Repository Delete working fine
//TODO				Removing Profile Picture from Path in which it was save Assignment
				
				this.contactRepository.delete(contact);
				session.setAttribute("message", new Message("Contact deleted Sucessfuly", "success"));
				
				}else {
//System.out.println("DELETED");
					session.setAttribute("message", new Message("You don't have permission..", "danger"));
				}
		return "redirect:/user/show_contact/0";
	}

//	open update form Handler
	@PostMapping("/update-contact/{cid}")
	public String updateForm(@PathVariable("cid") Integer cid, Model m) {
		
		m.addAttribute("title","Update-Contact");
		Contact contact = this.contactRepository.findById(cid).get();
		m.addAttribute("contact", contact);
		return "normal/update_form";
	}
//	process-update Contact
	@RequestMapping(value="/process-update",method=RequestMethod.POST)
	public String updateHandler(@ModelAttribute Contact contact,@RequestParam("profileImage") MultipartFile file
			,Model m, HttpSession session, Principal principal) {
		System.out.println("CONTACT NAME" + contact.getName());
		System.out.println("CONTACT ID" + contact.getCid());
// TODO File Check is Empty or Same 
		try {
			Contact oldcontactDetail = this.contactRepository.findById(contact.getCid()).get();
			
			
			if(!file.isEmpty()) {
//				Delete old photo update new photo
				
			}else {
				contact.setImageUrl(oldcontactDetail.getImageUrl());
			}
			
			CUser user = this.userRepository.getUserByUserName(principal.getName());
			contact.setUser(user);
			contact.setImageUrl("contact.png");
			this.contactRepository.save(contact);
		} catch (Exception e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
//Your Profile handler
	@GetMapping("/profile")
	public String settingProfile(Model model) {
		model.addAttribute("title","Profile Page");
		return "normal/profile";
	}
	
	@GetMapping("/add-lead")
	public String openAddLead(Model model) {
		model.addAttribute("title", "Add Lead");
//		model.addAttribute("contact", new Contact());
		return "/normal/add_Lead";
	}
// Item Creation 	
	@RequestMapping("/add-item")
	public String openAddItem(Model model) {
		model.addAttribute("title", "Add Item");
//		model.addAttribute("contact", new Contact());
		return "normal/add_item_form";
	}
	@PostMapping("/process-item")
	public String processItem(@ModelAttribute ItemMaster item, Principal principal) {
		CUser user = this.userRepository.getUserByUserName(principal.getName());
		item.setCreateOn(new Date());
		item.setCreatedBy(user.getName());
		try {
			this.itemRepository.save(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(item);
		return "normal/add_item_form";
	}
}
