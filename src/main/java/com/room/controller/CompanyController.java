package com.room.controller;

import com.room.common.R;
import com.room.entity.ComListEntity;
import com.room.service.ComInfoService;
import com.room.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Api(value = "CompanyController", description = "公司信息")
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private ComInfoService comInfoService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "创建新公司信息", notes = "创建新公司信息")
    @RequestMapping(value = "/createcom")
    public R userCreateCom(@RequestBody ComListEntity comListEntity){
        if(comListEntity.getComName()==null){
            return R.error("请输入公司名称!");
        }
        if(comListEntity.getRelName()==null){
            return  R.error("请输入真实姓名!");
        }
        if(comListEntity.geteMailNum()==null){
            return  R.error("请输入邮箱!");
        }
        R r= comInfoService.createComInfo(comListEntity);
        return r;
    }

    @ApiOperation(value = "加入已有公司", notes = "加入已有公司")
    @RequestMapping(value = "/joincom")
    public R userJoinCom(@RequestParam Map<String, Object> params) throws IOException {
        String username = (String) params.get("username");//获取前端传过来的userName
        if(null == username || username.trim().length() <= 0)
            return R.error("用户名不能为空");
        R r= userService.login(params);
        return r;
    }
}
