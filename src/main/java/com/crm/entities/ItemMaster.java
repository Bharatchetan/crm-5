package com.crm.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class ItemMaster {
	public String getIgroup() {
		return igroup;
	}
	public void setIgroup(String igroup) {
		this.igroup = igroup;
	}
	public ItemMaster(long id, String name, String uom, String type, String igroup, double costPrice,
			double purchaseCostPrice, double salePrice, Date createOn, Date editOn, String createdBy, String editedBy) {
		super();
		this.id = id;
		this.name = name;
		this.uom = uom;
		this.type = type;
		this.igroup = igroup;
		this.costPrice = costPrice;
		this.purchaseCostPrice = purchaseCostPrice;
		this.salePrice = salePrice;
		this.createOn = createOn;
		this.editOn = editOn;
		this.createdBy = createdBy;
		this.editedBy = editedBy;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String uom;
	private String type;
	private String igroup;
	private double costPrice;
	private double purchaseCostPrice;
	private double salePrice;
	private Date createOn;
	private Date editOn;
	private String createdBy;
	private String editedBy;
	public ItemMaster() {
		super();
		
	}
	@Override
	public String toString() {
		return "ItemMaster [id=" + id + ", name=" + name + ", uom=" + uom + ", type=" + type + ", igroup=" + igroup
				+ ", costPrice=" + costPrice + ", purchaseCostPrice=" + purchaseCostPrice + ", salePrice=" + salePrice
				+ ", createOn=" + createOn + ", editOn=" + editOn + ", createdBy=" + createdBy + ", editedBy="
				+ editedBy + "]";
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
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}
	public double getPurchaseCostPrice() {
		return purchaseCostPrice;
	}
	public void setPurchaseCostPrice(double purchaseCostPrice) {
		this.purchaseCostPrice = purchaseCostPrice;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	public Date getEditOn() {
		return editOn;
	}
	public void setEditOn(Date editOn) {
		this.editOn = editOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getEditedBy() {
		return editedBy;
	}
	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}
	
	
}
