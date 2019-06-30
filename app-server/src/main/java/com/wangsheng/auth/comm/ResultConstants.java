package com.wangsheng.auth.comm;

import com.wangsheng.comm.BaseResponse;

public class ResultConstants {

	public final static BaseResponse SUCCESS = new BaseResponse("0", "success");
	
	/* 没有权限 */
	public final static BaseResponse PERMISSION_DENIED = new BaseResponse("401", "Permission denied");

	/* 用户不存在 */
	public final static BaseResponse NO_USER = new BaseResponse("5000001", "user not exsit");

	/* 密碼錯誤 */
	public final static BaseResponse ERROR_PASS = new BaseResponse("5000002", "password if roww");

	/* token不存在或者已失效 */
	public final static BaseResponse ERROR_TOKEN = new BaseResponse("5000003", "token is invalid");

	/* 找不到商品 */
	public final static BaseResponse GOOD_NOT_FOUND = new BaseResponse("5000004", "not found good info");

	/* 文件为空 */
	public final static BaseResponse FILE_EMPTY = new BaseResponse("5000005", "file is empty");

	/*商品已存在 */
	public final static BaseResponse GOOD_EXSIT = new BaseResponse("5000006", "good code is already exsit");
	
	/*请求有误 */
	public final static BaseResponse REQ_ERROR = new BaseResponse("5000007", "request is error");

	/*连接码有误 */
	public final static BaseResponse CONNECT_ERROR = new BaseResponse("5000008", "connectcode is invalid");
	
	/*连接码有误 */
	public final static BaseResponse NO_SHOP_ERROR = new BaseResponse("5000009", "the user do not have any shop");

	/*结算已存在 */
	public final static BaseResponse SETTLEMNT_EXIST = new BaseResponse("5000010", "settlement exsit");

	/*验证码已过期 */
	public final static BaseResponse VALIDATE_CODE_EXPIRED = new BaseResponse("5000011", "validate_code_expired");
	
	/*用户名已存在 */
	public final static BaseResponse USERNAME_EXIST = new BaseResponse("5000012", "user name is exsit");

	/*验证码错误 */
	public final static BaseResponse VALIDATE_CODE_WRONG = new BaseResponse("5000013", "validate_code_ wrong");

	/*邮箱已存在*/
	public final static BaseResponse EMAIL_EXIST = new BaseResponse("5000014", "exsit email");
}
