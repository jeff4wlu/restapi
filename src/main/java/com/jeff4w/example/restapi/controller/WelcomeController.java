package com.jeff4w.example.restapi.controller;

import com.jeff4w.example.restapi.common.Restful.ResponseResult;
import com.jeff4w.example.restapi.common.Restful.RestResultGenerator;
import com.jeff4w.example.restapi.config.JWT.JWTUtil;
import com.jeff4w.example.restapi.domain.UserInfo;
import com.jeff4w.example.restapi.service.StudentService;
import com.jeff4w.example.restapi.service.UserInfoService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.UnauthorizedException;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-01 9:23
 */

@RestController
@Api("登录等功能的api")
public class WelcomeController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisManager redisManager;

    @ResponseBody
    @PostMapping("/login")
    public ResponseResult<String> login(@RequestParam("username") String username,
                                @RequestParam("password") String password) {
        String flashToken = null;
        try{
            UserInfo userInfo = userInfoService.findByUsername(username);
            if (userInfo.getPassword().equals(password)) {
                flashToken = JWTUtil.sign(username, password);
                String tmp = redisManager.getJedisPool().getResource().setex(username, 300, flashToken);
            } else {
                throw new UnauthorizedException();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            redisManager.getJedisPool().getResource().close();
        }
        return RestResultGenerator.genSuccessResult(flashToken);
    }

}
