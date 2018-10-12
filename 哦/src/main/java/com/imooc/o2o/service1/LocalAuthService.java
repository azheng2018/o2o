package com.imooc.o2o.service1;



import com.imooc.o2o.dto.LocalAuthExcution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.exceptions.LocalAuthOperationException;

public interface LocalAuthService {

	/**
	 * 通过账号和密码来获取平台账号信息
	 */
	LocalAuth getLocalAuthByUsernameAndPwd(String username,String password);
	/**
	 * 通过userId获取平台账号信息
	 */
	LocalAuth getLocalAuthBuUserId(long userId);
	/**
	 * 绑定微信，生成平台专属账号
	 */
	LocalAuthExcution bindLocalAuth(LocalAuth localAuth)throws LocalAuthOperationException;
	/**
	 * 修改平台账号的登录Miami
	 */
	LocalAuthExcution modifyLocalAuth(Long userId,String username,String password,String newPassword)
	throws LocalAuthOperationException;
}
