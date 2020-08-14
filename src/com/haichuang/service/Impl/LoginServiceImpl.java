package com.haichuang.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haichuang.bean.Login;
import com.haichuang.dao.LoginMapper;
import com.haichuang.service.ILoginService;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private LoginMapper dao;
	public void setDao(LoginMapper dao) {
		this.dao = dao;
	}
	@Override
	public int deleteByUsername(String username) {
	
		
		return 0;
	}

	@Override
	public int insert(Login record) {
		int i=0;
		i=dao.insert(record);
		return i;
	}

	@Override
	public Login selectByUsername(Login record) {
		return dao.selectByUsername(record);
	}

	@Override
	public int updateByUsername(Login record) {
		return 0;
	}

}
