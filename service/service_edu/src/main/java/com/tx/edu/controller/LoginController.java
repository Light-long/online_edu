package com.tx.edu.controller;

import com.tx.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟登陆接口，之后会整合SpringSecurity和数据库
 */
@RestController
@RequestMapping("/edu/user")
@CrossOrigin
@Api(description = "模拟登陆")
public class LoginController {

    @PostMapping("/login")
    @ApiOperation("登陆")
    public CommonResult login() {
        return CommonResult.ok().data("token", "admin");
    }

    @GetMapping("/info")
    @ApiOperation("获取信息")
    public CommonResult info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("name", "admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return CommonResult.ok().data(map);
    }
}
