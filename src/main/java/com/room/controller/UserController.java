package com.room.controller;

import com.room.common.R;
import com.room.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Api(value = "UserController", description = "用户信息")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    private static final Logger logger = Logger.getLogger(UserController.class);


    /**
     * 用户是否是第一次登录小程序
     */
    @ApiOperation(value = "判断用户是否是第一次登录小程序", notes = "判断用户是否是第一次登录小程序")
    @RequestMapping(value = "/firstLogin")
    public R userLoginFirst(@RequestParam String wxId){      //这个方法目前没有被调用
//        logger.info("判断用户是否是第一次登录小程序");
        if (wxId==null || "".equals(wxId.trim()))
            return R.error("参数错误");
        Map<String, Object> result = userService.userLoginFirst(wxId);
        return R.ok().put("obj",result);
    }

    /**
     * 用户登录
     */
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @RequestMapping(value = "/login")
    public Map<String, Object> login(@RequestParam Map<String, Object> params) throws IOException {
//        logger.info("用户登录");
        String userName = (String) params.get("userName");//获取前端传过来的userName
        if(null == userName || userName.trim().length() <= 0)
            return R.error("用户名不能为空");
        R r = userService.login(params);
        return r;
    }

    /**
     * 修改密码
     */
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @RequestMapping(value = "/modify")
    public R modifyPassword(@RequestParam Map<String, Object> params){
//        logger.info("修改密码");
        R r = userService.modifyPassword(params);
        return r;
    }

    /*
     *  验证邀请码
     */
    @ApiOperation(value = "验证邀请码", notes = "验证邀请码")
    @RequestMapping(value = "/checkInvite")
    public R checkInvite(@RequestParam Map<String, Object> params) {
//        logger.info("验证邀请码");
        R r = userService.checkInvite(params);
        return r;
    }

    /*
     *   更新邀请码
     */
    @ApiOperation(value = "更新邀请码", notes = "更新邀请码")
    @RequestMapping(value = "/updateInvite")
    public R updateInvite(@RequestParam Map<String, String> params) {
//        logger.info("修改邀请码");
        R r = userService.updateInvite(params);
        return r;
    }

}
