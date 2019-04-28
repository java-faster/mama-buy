package com.room.service.impl;

import com.room.common.R;
import com.room.dao.room.UserTokenDao;
import com.room.entity.UserTokenEntity;
import com.room.oauth2.TokenGenerator;
import com.room.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("userTokenService")
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
    private UserTokenDao userTokenDao;

    //过期时间(24小时)
    private final static int EXPIRE = 3600 * 24;

    @Override
    public UserTokenEntity queryByUserId(Long userId) {
        return userTokenDao.queryByUserId(userId);
    }

    @Override
    public void save(UserTokenEntity tokenEntity) {
        userTokenDao.save(tokenEntity);
    }

    @Override
    public void update(UserTokenEntity tokenEntity) {
        userTokenDao.update(tokenEntity);
    }

    @Override
    public R createToken(long userId){
        //生成一个token
        String token = TokenGenerator.generateValue();
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        //判断是否生成过token
        UserTokenEntity tokenEntity = queryByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new UserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //保存token
            save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //更新token
            update(tokenEntity);
        }
        R r = R.ok().put("token", token).put("expire", EXPIRE);
        return r;
    }
}
