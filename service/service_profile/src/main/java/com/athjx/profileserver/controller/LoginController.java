package com.athjx.profileserver.controller;

import com.athjx.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登陆接口")
@RestController
@RequestMapping("/endService/user")
@CrossOrigin//跨域
public class LoginController {
    @PostMapping("login")
    public R userLogin(){


    return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R loginInfo(){


        return R.ok().data("roles","[admim]").data("name","郝嘉祥")
                .data("avatar","http://a0.att.hudong.com/64/76/20300001349415131407760417677.jpg").data("introduction","简介");
    }
}
