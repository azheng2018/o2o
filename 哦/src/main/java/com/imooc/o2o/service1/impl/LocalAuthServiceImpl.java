package com.imooc.o2o.service1.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.LocalAuthDao;
import com.imooc.o2o.dto.LocalAuthExcution;
import com.imooc.o2o.entity.LocalAuth;
import com.imooc.o2o.eunms.LocalAuthStateEnum;
import com.imooc.o2o.exceptions.LocalAuthOperationException;
import com.imooc.o2o.service1.LocalAuthService;
import com.imooc.o2o.util.MD5;

@Service
public class LocalAuthServiceImpl implements LocalAuthService{
	@Autowired 
	private LocalAuthDao localAuthDao;

	@Override
	public LocalAuth getLocalAuthByUsernameAndPwd(String username, String password) {
		return localAuthDao.queryLocalByUserNameAndPwd(username,MD5.getMD5(password));
	}

	@Override
	public LocalAuth getLocalAuthBuUserId(long userId) {
		return localAuthDao.queryLocalByUserId(userId);
	}

	@Override
	@Transactional
	public LocalAuthExcution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
		//空值判断，传入localAuth账号密码，用户信息特别是userId不能为空，否则直接返回错误
		if(localAuth==null||localAuth.getPassword()==null||localAuth.getUsername()==null||localAuth.getPersonInfo()==null
				||localAuth.getPersonInfo().getUserId()==null){
			return new LocalAuthExcution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		//查询该用户是否绑定过平台账号
		LocalAuth tempAuth=localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
		if (tempAuth!=null) {
			//如果绑定过平台账号则退出，保证平台账号的唯一性
			return new LocalAuthExcution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			//如果之前没绑定过平台账号，则创建一个平台账号与该用户绑定
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			localAuth.setPassword(MD5.getMD5(localAuth.getPassword()));
			int num=localAuthDao.insertLocalAuth(localAuth);
			//判断是否创建成功
			if (num<=0) {
				throw new LocalAuthOperationException("账号绑定失败");
			}else {
				return new LocalAuthExcution(LocalAuthStateEnum.SUCCESS, localAuth);
			}
		} catch (Exception e) {
			throw new LocalAuthOperationException("账号绑定失败"+e.toString());
		}
	}

	@Override
	@Transactional
	public LocalAuthExcution modifyLocalAuth(Long userId, String username, String password, String newPassword) throws LocalAuthOperationException{
		//非空判断,若传入的用户id，新旧密码是否为空，新旧密码是否相同，若不满足条件直接返回错误信息
		if (userId!=null&&username!=null&&newPassword!=null&&password!=null&&!password.equals(newPassword)) {
		try {
			//更新密码
			int num=localAuthDao.updateLocalAuth(userId, username, MD5.getMD5(password), 
					MD5.getMD5(newPassword),new Date());
			//判断是否更新成功
			if (num<=0) {
				throw new LocalAuthOperationException("密码更新失败");
			}
			return new LocalAuthExcution(LocalAuthStateEnum.SUCCESS);
		} catch (Exception e) {
			throw new LocalAuthOperationException("errMsg"+e.toString());
		}
		} else {
			return new LocalAuthExcution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		
	}
	
	

}
