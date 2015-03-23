 package com.topit.datacopy.utils;

import java.security.MessageDigest;
 

 /** 
* @ClassName: MD5encrypt 
* @Description: md5加密 
* @author qiugui
* @date 2015年3月16日 下午2:35:50 
*  
*/ 
public class MD5Encrypt {

	 public static String encipher(String strs) throws Exception{
		 char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b',
				 'c','d','e','f'};
		 
		byte[] strTemp=strs.getBytes();
		
		MessageDigest messageDigestTemp=MessageDigest.getInstance("MD5");
		
		messageDigestTemp.update(strTemp);
		
		byte[] messageDigest=messageDigestTemp.digest();
		
		int j=messageDigest.length;
		
		char str[]=new char[j*2];
		
		int k=0;
		
		for(int i=0;i<j;i++){
			byte byte0=messageDigest[i];
			
			str[k++]=hexDigits[byte0 >>> 4 & 0xf];
			str[k++]=hexDigits[byte0 & 0xf];
		}
		
		return new String(str);
	}
}

 