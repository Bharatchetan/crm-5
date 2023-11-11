package com.crm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crm.entities.BasicEnquiry;


public interface BasicEnquiryRepository extends JpaRepository<BasicEnquiry, Long>{
//	@Query("from BasicEnquiry as c where c.BasicEnquiry.id=:Id")
//	public Page<BasicEnquiry> findBasicEnquiryById(@Param("Id") long Id, Pageable pePageable);
//	public List<BasicEnquiry> findBY
}
