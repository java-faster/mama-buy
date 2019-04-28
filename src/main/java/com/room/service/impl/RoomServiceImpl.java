package com.room.service.impl;

import com.room.dao.room.RoomDao;
import com.room.entity.RoomEntity;
import com.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roomService")
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Override
    public List<String> getRoomList() {
        return roomDao.queryList();
    }

    @Override
    public RoomEntity getRoomInfo(String roomName) {
        return roomDao.queryOne(roomName);
    }
}
