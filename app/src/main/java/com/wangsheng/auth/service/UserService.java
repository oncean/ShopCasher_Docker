package com.wangsheng.auth.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.auth.dao.RegistHisDao;
import com.wangsheng.auth.dao.UserDao;
import com.wangsheng.auth.data.RegistHisInfo;
import com.wangsheng.auth.data.UserInfo;
import com.wangsheng.auth.model.RegistStatus;
import com.wangsheng.auth.model.UserCreateType;
import com.wangsheng.auth.model.UserStatusConstants;
import com.wangsheng.comm.utils.UUIDGenerator;
import com.wangsheng.exception.ServiceException;

@Service
public class UserService {

	@Autowired
	private RegistHisDao registHisDao;

	@Autowired
	private UserDao userDao;

	public UserInfo getUserByOpenId(String openId) {
		return userDao.getByOpenId(openId);
	}

	/**
	 * 新建微信用户
	 * 
	 * @param openId
	 * @return
	 */
	public UserInfo createUserFromWX(String openId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(UUIDGenerator.randomID());
		userInfo.setOpenId(openId);
		userInfo.setCreateTime(new Date());
		userInfo.setCreateType(UserCreateType.WEIXIN);
		userInfo.setStatus(UserStatusConstants.CREATE);
		return userDao.save(userInfo);
	}

	/**
	 * 新建注册用户
	 * 
	 * @param openId
	 * @return
	 */
	public UserInfo createUserFromSystem(RegistHisInfo registHisInfo) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(UUIDGenerator.randomID());
		userInfo.setOpenId(null);
		userInfo.setCreateTime(new Date());
		userInfo.setCreateType(UserCreateType.SYSTEM);
		userInfo.setStatus(UserStatusConstants.CREATE);
		userInfo.setEmail(registHisInfo.getAddress());
		userInfo.setPassword(registHisInfo.getPassword());
		userInfo.setUserName(registHisInfo.getUserName());
		return userDao.save(userInfo);
	}

	/**
	 * 判断是否是新用户
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkIfNewUser(String userId) throws ServiceException {
		UserInfo userInfo = userDao.getOne(userId);
		if (userInfo == null) {
			throw new ServiceException(ResultConstants.NO_USER);
		}
		if (UserStatusConstants.CREATE == userInfo.getStatus()) {
			return true;
		}
		return false;
	}

	/**
	 * 更新user状态
	 * 
	 * @param userId
	 * @param status
	 */
	public void updateUserStatus(String userId, Integer status) {
		UserInfo userInfo = userDao.getOne(userId);
		userInfo.setStatus(status);
		userDao.save(userInfo);
	}

	public boolean checkEmailIfExsit(String email) {
		UserInfo userInfo = userDao.getByEmail(email);
		if (null == userInfo)
			return false;
		return true;
	}

	public boolean checkUserNameIfExsit(String userName) {
		UserInfo userInfo = userDao.getByUserName(userName);
		if (null == userInfo)
			return false;
		return true;
	}

	@Transactional
	public void regist(RegistHisInfo registHisInfo) {

		registHisInfo.setSuccessTime(new Date());
		registHisInfo.setStatus(RegistStatus.SUCCESS);
		registHisDao.save(registHisInfo);

		// 创建用户
		createUserFromSystem(registHisInfo);
	}
}
