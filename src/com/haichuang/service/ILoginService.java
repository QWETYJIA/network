package com.haichuang.service;

import com.haichuang.bean.Login;

public interface ILoginService {

	int deleteByUsername(String username);

    int insert(Login record);

    Login selectByUsername(Login record);

    int updateByUsername(Login record);
}
