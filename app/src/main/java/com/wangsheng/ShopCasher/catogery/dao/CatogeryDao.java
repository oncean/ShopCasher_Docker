package com.wangsheng.ShopCasher.catogery.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangsheng.ShopCasher.catogery.data.Catogery;

public interface CatogeryDao extends JpaRepository<Catogery, String> {
	
	List<Catogery> getByStatus(int status);
	
	List<Catogery> queryByTypeAndStatus(int type, int status);
	
}
