package com.crm.entities;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BasicEnquiry {

	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	@Override
	public String toString() {
		return "BasicEnquiry [id=" + id + ", name=" + name + ", contactno=" + contactno + ", companyName=" + companyName
				+ ", industryType=" + industryType + ", email=" + email + ", website=" + website + ", queryAbout="
				+ queryAbout + ", createOn=" + createOn + "]";
	}
	public BasicEnquiry() {
		super();
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getContactno() {
		return contactno;
	}
	public void setContactno(long contactno) {
		this.contactno = contactno;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getQueryAbout() {
		return queryAbout;
	}
	public void setQueryAbout(String queryAbout) {
		this.queryAbout = queryAbout;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private long contactno;
	private String companyName;
	private String industryType;
	private String email;
	private String website;
	private String queryAbout;
	private Date createOn;
	}
