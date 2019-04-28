package com.room.dao.room;

import com.room.dao.BaseDao;
import com.room.entity.RoomEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomDao extends BaseDao<RoomEntity> {
    List<String> queryList();

    RoomEntity queryOne(String rooomName);
}
