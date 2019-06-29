package com.wangsheng.file.controller;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wangsheng.ConfigProperty.AppConfig;

@Service
public class FileService {

    @Autowired
    private HttpServletRequest request;
    
	@Autowired
	private AppConfig appConfig;

	public String saveFile(MultipartFile file) throws Exception{
		String result = null;
		if (!file.isEmpty()) {
			// 可以对user做一些操作如存入数据库
			// 以下的代码是将文件file重新命名并存入Tomcat的webapps目录下项目的下级目录fileDir
			String fileRealName = file.getOriginalFilename(); // 获得原始文件名;
			int pointIndex = fileRealName.indexOf("."); // 点号的位置
			String fileSuffix = fileRealName.substring(pointIndex); // 截取文件后缀
			UUID FileId = UUID.randomUUID(); // 生成文件的前缀包含连字符
			String savedFileName = FileId.toString().replace("-", "").concat(fileSuffix); // 文件存取名
			String savedDir = request.getSession().getServletContext().getRealPath(appConfig.getUploadGoodFileDir()); // 获取服务器指定文件存取路径
			File savedFile = new File(savedDir, savedFileName);
			result = "/" + appConfig.getUploadGoodFileDir() + "/" + savedFileName;
			
				File saveDirFile = new File(savedDir);
				if(!saveDirFile.exists()){
					saveDirFile.mkdirs();
				}
				boolean isCreateSuccess = savedFile.createNewFile();
				if (isCreateSuccess) {
					file.transferTo(savedFile); // 转存文件
				}
		}else{
			throw new Exception();
		}
		return result;
	}
	
}
