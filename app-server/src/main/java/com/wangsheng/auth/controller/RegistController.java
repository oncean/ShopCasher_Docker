package com.wangsheng.auth.controller;

import java.util.Date;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.auth.dao.RegistHisDao;
import com.wangsheng.auth.data.RegistHisInfo;
import com.wangsheng.auth.model.RegistRequest;
import com.wangsheng.auth.model.RegistStatus;
import com.wangsheng.auth.service.UserService;
import com.wangsheng.comm.BaseResponse;
import com.wangsheng.comm.utils.EmailUtil;
import com.wangsheng.comm.utils.UUIDGenerator;
import com.wangsheng.comm.utils.entity.MessageInfo;

@RestController
@RequestMapping("/regist")
public class RegistController {

	@Autowired
	private RegistHisDao registHisDao;

	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/sendCodeToEmail")
	public BaseResponse sendCodeToEmail(@RequestBody RegistRequest req) {
		RegistHisInfo registHisInfo = new RegistHisInfo();
		registHisInfo.setId(UUIDGenerator.ID());
		BeanUtils.copyProperties(req, registHisInfo);
		String code = UUIDGenerator.randomShortNum();
		registHisInfo.setCode(code);
		Date createTime = new Date();
		registHisInfo.setCreateTime(createTime);
		Date expiredTime = new Date(createTime.getTime() + 600000);
		registHisInfo.setExpiredTime(expiredTime);
		registHisInfo.setStatus(RegistStatus.WAITING);

		MessageInfo messageInfo = new MessageInfo(req.getAddress(), "【云收银】账号激活邮件");
		StringBuilder sBuilder = new StringBuilder("感谢您注册入驻云收银平台");
		sBuilder.append("，您的验证码是：");
		sBuilder.append(code);
		sBuilder.append(",请尽快输入");
		messageInfo.setContent(sBuilder.toString());

		try {
			emailUtil.send(messageInfo);
		} catch (Exception e) {
			return new BaseResponse(false);
		}

		// 将之前的等待中记录status设置为过期
		List<RegistHisInfo> registHisInfos = registHisDao.getByAddressAndStatus(req.getAddress(), RegistStatus.WAITING);
		for (RegistHisInfo info : registHisInfos) {
			info.setStatus(RegistStatus.EXPIRED);
		}
		registHisDao.save(registHisInfos);

		registHisDao.save(registHisInfo);

		return new BaseResponse(true);
	}

	@PostMapping
	public BaseResponse regist(@RequestBody RegistRequest req) {

		// 校验验证码
		List<RegistHisInfo> registHisInfos = registHisDao.getByAddressAndStatusAndCode(req.getAddress(),
				RegistStatus.WAITING, req.getCode());
		if (CollectionUtils.isEmpty(registHisInfos)) {
			return ResultConstants.VALIDATE_CODE_EXPIRED;
		}

		RegistHisInfo registHisInfo = registHisInfos.get(0);
		if (new Date().after(registHisInfo.getExpiredTime())) {
			return ResultConstants.VALIDATE_CODE_EXPIRED;
		}

		if (!registHisInfo.getCode().equals(req.getCode())) {
			return ResultConstants.VALIDATE_CODE_WRONG;
		}

		// 校验用户名
		if (userService.checkUserNameIfExsit(req.getUserName())) {
			return ResultConstants.USERNAME_EXIST;
		}

		// 校验邮箱
		if (userService.checkEmailIfExsit(req.getAddress())) {
			return ResultConstants.EMAIL_EXIST;
		}

		// 保存注册记录
		BeanUtils.copyProperties(req, registHisInfo);
		userService.regist(registHisInfo);
		return ResultConstants.SUCCESS;
	}

	@GetMapping(value = "/checkEmailExsit")
	public BaseResponse checkEmailExsit(@QueryParam("email") String email) {
		return new BaseResponse(userService.checkEmailIfExsit(email));
	}

	@GetMapping(value = "/checkUserNameExsit")
	public BaseResponse checkUserNameExsit(@QueryParam("userName") String userName) {
		return new BaseResponse(userService.checkUserNameIfExsit(userName));
	}
}
