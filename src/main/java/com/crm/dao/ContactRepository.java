package com.crm.dao;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crm.entities.CUser;
import com.crm.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer>{
//Pagination
//	currentPage-page
//	Contact per Page-5
	@Query("from Contact as c where c.user.id=:userId")
	public Page<Contact> findContactByCUser(@Param("userId") int userId, Pageable pePageable);
	
//	public List<Contact> findContactByCUser(@Param("userId") int userId);
//	Searching requested name
	public List<Contact> findByNameContainingAndUser(String name,CUser user);
//
//	public List<Contact> findByNameContainingAndCUser(String query, com.thymeleaf.contactmanager.entities.CUser cuser);
//	@Query("select * from contact where user_id=3 order by cid desc limit 5;")
/*
 * 		@Query("from contact as c where c.user_id=userId order by c.cid desc limit 5")
*	public List<Contact> findByNameContainingAndUserL(String name,int userId, CUser user);
*/
}
