package com.wangsheng.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.comm.BaseResponse;

@RestController
public class FileController {

	@Autowired
	private FileService fileService;


	@RequestMapping(value = "/fileUpload")
	public BaseResponse fileUpload(@RequestParam("file") MultipartFile file) {
		BaseResponse rsp = ResultConstants.SUCCESS;
		try {
			rsp.setResult(fileService.saveFile(file));
		} catch (Exception e) {
			return ResultConstants.FILE_EMPTY;
		}
		return rsp;
	}


	@RequestMapping(value = "/filesUpload")
	public BaseResponse filesUpload(@RequestParam("files") MultipartFile file) {
		BaseResponse rsp = ResultConstants.SUCCESS;
		try {
			rsp.setResult(fileService.saveFile(file));
		} catch (Exception e) {
			return ResultConstants.FILE_EMPTY;
		}
		return rsp;
	}
}
