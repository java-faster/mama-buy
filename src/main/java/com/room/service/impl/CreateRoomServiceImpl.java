package com.room.service.impl;

import com.room.common.R;
import com.room.dao.ledger.UserDao;
import com.room.entity.RoomEntity;
import com.room.service.CreateRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service("CreateRoomimpl")
public class CreateRoomServiceImpl implements CreateRoomService {

    @Autowired
    private UserDao userDao;

    @Override
    public R createRoom(RoomEntity roomEntity) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //得到当前时间字符串
        String dateNowStr = sdf.format(d);
        roomEntity.setCreateDate(dateNowStr);
        userDao.insertRoomInfo(roomEntity);
        return R.ok("创建成功!");
    }
}
