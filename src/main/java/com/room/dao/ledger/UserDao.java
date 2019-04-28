package com.room.dao.ledger;

import com.room.dao.BaseDao;
import com.room.entity.ComListEntity;
import com.room.entity.RoomEntity;
import com.room.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserDao extends BaseDao<UserEntity> {

    //根据微信名或用户名查询用户信息
//    UserEntity queryUserName(String name);
    UserEntity queryUserName(Map<String, Object> name);

//    String queryUserNameByUserId(Map<String,Object> params);

    String queryUserNameByUserId(String userId);

    int addNewUser(UserEntity user);

    String selectOpenId(String userName);

    int existUser(String userName);

    //根据用户名更新登录状态
    Integer updateLoginStateByName(String name);

    //根据用户名查询登录状态
    Integer selectLoginState(String name);

    Integer checkOpenIdIsEqual(String openId);


    Integer selectRoleByUserName(String userName);

    Integer selectRoleByUserId(Integer userId);

    Integer selectUserIdByName(String userName);

    Integer selectUserIdByOpenId(String openId);

    String selectUserNameByOpenId(String openId);

    Integer selectLoginStateByOpenId(String openId);

    Integer selectRoleByOpenId(String openId);

    Integer checkUserById(Integer userId);

    Integer checkUserByName(String userName);

    void insertComInfo(ComListEntity comListEntity);

    void insertRoomInfo(RoomEntity roomEntity);
}
