package com.jeff4w.example.restapi.service;


import com.jeff4w.example.restapi.domain.UserInfo;

public interface UserInfoService {

    public UserInfo findByUsername(String username);

}
