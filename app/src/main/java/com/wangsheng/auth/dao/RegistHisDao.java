package com.wangsheng.auth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangsheng.auth.data.RegistHisInfo;

public interface RegistHisDao extends JpaRepository<RegistHisInfo, String> {

	List<RegistHisInfo> getByAddressAndStatus(String address, Integer status);

	List<RegistHisInfo> getByAddressAndStatusAndCode(String address, Integer status, String code);
}
