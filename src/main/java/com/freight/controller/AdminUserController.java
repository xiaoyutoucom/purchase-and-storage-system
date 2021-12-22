package com.freight.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.freight.annotation.JwtIgnore;
import com.freight.common.R;
import com.freight.entity.Freight_user;
import com.freight.entity.JWT.Audience;
import com.freight.service.FREIGHT_USERervice;
import com.freight.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/7/18 10:41
 * Version: v1.0
 * ========================
 */
@Slf4j
@RestController
public class AdminUserController {
    @Resource
    private FREIGHT_USERervice userservice;
    @Autowired
    private Audience audience;
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value="/login", method= RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostMapping("/login")
    @JwtIgnore
    public R adminLogin(HttpServletResponse response, String username, String password) {
        try {
        QueryWrapper<Freight_user> userQueryWrapper = Wrappers.query();

        Map<String , Object> map = new HashMap<>();
        map.put("username" , username);
        map.put("password" , password);

        userQueryWrapper.allEq(map);
        Freight_user fu = userservice.getOne(userQueryWrapper);
        // 这里模拟测试, 默认登录成功，返回用户ID和角色信息
        String role = "admin";

        // 创建token
        String token = JwtTokenUtil.createJWT(fu.getId(), fu.getUsername(), role, audience);
        log.info("### 登录成功, token={} ###", token);

        // 将token放在响应头
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        // 将token响应给客户端
        JSONObject result = new JSONObject();
        result.put("accountid", fu.getAccountid());
        result.put("token", token);
        return R.ok().message("登陆成功").data(result);

        }
        catch (Exception e) {
            return R.error().message("登陆失败");
        }
    }

//    @GetMapping("/users")
//    public Result userList() {
//        log.info("### 查询所有用户列表 ###");
//        return Result.SUCCESS();
//    }
}
