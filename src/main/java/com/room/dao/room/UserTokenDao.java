package com.room.dao.room;

import com.room.dao.BaseDao;
import com.room.entity.UserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserTokenDao extends BaseDao<UserTokenEntity> {

    UserTokenEntity queryByUserId(Long userId);
}
