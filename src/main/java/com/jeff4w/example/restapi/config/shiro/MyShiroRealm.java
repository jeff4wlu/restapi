package com.jeff4w.example.restapi.config.shiro;

import com.jeff4w.example.restapi.config.JWT.JWTToken;
import com.jeff4w.example.restapi.config.JWT.JWTUtil;
import com.jeff4w.example.restapi.domain.SysPermission;
import com.jeff4w.example.restapi.domain.SysRole;
import com.jeff4w.example.restapi.domain.UserInfo;
import com.jeff4w.example.restapi.service.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 身份校验核心类
 *
 * @author Administrator
 *
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private UserInfoService userInfoService;

    @Autowired
    private RedisManager redisManager;

    /**
     * 认证信息(身份验证) Authentication 是用来验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");

        // 获取用户的输入帐号
        String token = (String) auth.getCredentials();
        //先从cache中找token，如果无则超时，需要继续判断是否要刷新token
        String username = redisManager.getJedisPool().getResource().get(token);
        if (username == null) {
            //cache已超时，username只能从客户端传入的token中解码出来
            username = JWTUtil.getUsername(token);
            UserInfo userInfo = userInfoService.findByUsername(username);
            if (userInfo == null) {
                throw new AuthenticationException("User didn't existed!");
            }
            if (!JWTUtil.verify(token, username, userInfo.getPassword())) {
                throw new AuthenticationException("token超时或不合法");
            }
            //刷新token并存入cache和threadholder,并删除旧的token
            String flashToken = JWTUtil.sign(username, userInfo.getPassword());
            redisManager.getJedisPool().getResource().del(token);
            redisManager.getJedisPool().getResource().set(flashToken, username,"NX", "EX",300);
            JWTUtil.flashToken.set(flashToken);//以便后面API返回给用户时带走新token
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");

        String username = JWTUtil.getUsername(principals.toString());
        UserInfo userInfo = userInfoService.findByUsername(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<SysRole> roles = userInfo.getRoleList();
        Set<String> permissionSet;
        for(SysRole role : roles){
            simpleAuthorizationInfo.addRole(role.getRole());
            List<SysPermission> permissions = role.getPermissions();
            permissionSet = new HashSet<>();
            for(SysPermission sysPermission : permissions){
                permissionSet.add(sysPermission.getPermission());
            }
            simpleAuthorizationInfo.addStringPermissions(permissionSet);
        }

        return simpleAuthorizationInfo;
    }

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }


}
