package com.haichuang.dao;

import com.haichuang.bean.Login;

public interface LoginMapper {
    int deleteByUsername(String username);

    int insert(Login record);

    Login selectByUsername(Login record);

    int updateByUsername(Login record);

}