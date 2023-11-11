package com.crm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.entities.ItemMaster;

public interface ItemRepository extends JpaRepository<ItemMaster, Long> {

}
