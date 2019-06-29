package com.wangsheng.comm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class UUIDGenerator {

    /** 
     * 获得一个UUID 
     * @return String UUID 
     */
    public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    }

	/**
	 * 随机码生成  16进制
	 * @return
	 */
	public static String random() {
		String uuid = getUUID();
		String sdf = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
		return uuid + sdf;
	}
	

	/**
	 * 随机码生成 64进制  16位 
	 * @return
	 */
	public static String randomID() {
		String uuid = random();
		String[] url = shortUrl(uuid);
		StringBuffer result= new StringBuffer();
		for (String string : url) {
			result.append(string);
		}
		return result.toString();
	}

	/**
	 * 随机码生成 64进制  16位 
	 * @return
	 */
	public static String ID() {
		return randomID();
	}
	
	
	private static String[] shortUrl(String url) { 
        // 可以自定义生成 MD5 加密字符传前的混合 KEY  
        String key = "";  
        // 要使用生成 URL 的字符  
        String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h",  
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",  
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",  
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",  
                "U", "V", "W", "X", "Y", "Z" };  
  
        // 对传入网址进行 MD5 加密  
        String sMD5EncryptResult = DigestUtils.md5Hex(key + url);  
        String hex = sMD5EncryptResult;  
        String[] resUrl = new String[4];  
        for (int i = 0; i < 4; i++) {  
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算  
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);  
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用  
            // long ，则会越界  
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);  
            String outChars = "";  
            for (int j = 0; j < 6; j++) {  
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引  
                long index = 0x0000003D & lHexLong;  
                // 把取得的字符相加  
                outChars += chars[(int) index];  
                // 每次循环按位右移 5 位  
                lHexLong = lHexLong >> 5;  
            }  
  
            // 把字符串存入对应索引的输出数组  
            resUrl[i] = outChars;  
        }  
        return resUrl;  
    }  

	/*
	 * 生成随机的6位随机码大小写字母
	 */
	public static String randomShortCode() {
		String random = random();
		String[] shortUrl = shortUrl(random);
		return shortUrl[0];
	}

	/*
	 * 生成随机的6位随机码 数字
	 */
	public static String randomShortNum() {
		String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了  
	    Random rand = new Random();  
	    StringBuffer flag = new StringBuffer();  
	    for (int j = 0; j < 6; j++)   
	    {  
	        flag.append(sources.charAt(rand.nextInt(9)) + "");  
	    }  
	    System.out.println(flag.toString());
		return flag.toString();
	}

	/*
	 * 生成随机的6位随机码 数字,不重复
	 */
	public static String randomShortNum(List<Integer> exsit) {
		Integer result = Integer.valueOf(randomShortNum());
		return String.valueOf(getNotRepeat(result,exsit));
	}
	
	private static Integer getNotRepeat(Integer result,List<Integer> exsit){
		if(exsit.contains(result)){
			result = result+1;
			return getNotRepeat(result,exsit);
		}else{
			return result;
		}
	}
}
