package com.crm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.crm.entities.CUser;

public interface UserRepository extends JpaRepository<CUser,Integer> {
	@Query("select u from CUser u where u.email=:email")
	public CUser getUserByUserName(@Param("email")String email);	
}
