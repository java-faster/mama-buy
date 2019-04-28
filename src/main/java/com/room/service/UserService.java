package com.room.service;

import com.room.common.R;
import com.room.entity.UserEntity;

import java.util.Map;

public interface UserService {
    //用户是否登录过小程序
    Map<String, Object> userLoginFirst(String wxId);

    R login(Map<String, Object> params);

    String getUserNameByUserId(String userId);

//    Integer getUserId(String userName);

    void upateUser(UserEntity userEntity);

    R modifyPassword(Map<String, Object> params);

    R checkInvite(Map<String, Object> params);

    R updateInvite(Map<String, String> params);

    Integer selectUserIdByOpenId(String openId);

    String selectUserNameByOpenId(String openId);

    Integer selectLoginStateByOpenId(String openId);

    Integer selectRoleByOpenId(String openId);

}
