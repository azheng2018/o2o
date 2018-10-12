package com.imooc.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceHolderConfiguer extends PropertyPlaceholderConfigurer{
//需要解密的字符数组
	String[] encryptProNames={"jdbc.username","jdbc.password"};
	
	/**
	 * 对关键的属性进行转换
	 */
	
	protected String convertProperty(String propertyName,String propertyValue){
		//判断是否为加密
		if (isEncryptPro(propertyName)) {
			String decryptValue=DESUtil.getDecodeString(propertyValue);
			return decryptValue;
		}
		return propertyValue;
	}
	/**
	 * 该属性是否加密判断
	 */
	private boolean isEncryptPro(String propertyName){
		for (String encryptProName : encryptProNames) 
			if (encryptProName.equals(propertyName)) {
				return true;
			}
		return false;
	}
}
