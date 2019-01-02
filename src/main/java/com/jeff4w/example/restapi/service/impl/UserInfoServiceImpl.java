package com.jeff4w.example.restapi.service.impl;

import com.jeff4w.example.restapi.domain.UserInfo;
import com.jeff4w.example.restapi.repository.UserInfoRepository;
import com.jeff4w.example.restapi.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoRepository userInfoRepository;

    @Transactional(readOnly=true)
    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoRepository.findByUsername(username);
    }

}