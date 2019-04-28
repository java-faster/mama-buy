package com.room.dao.room;

import com.room.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface WXSessionDao extends BaseDao {
    String selectOpenId(String sessionId);

    String selectSessionId(String openId);
}
