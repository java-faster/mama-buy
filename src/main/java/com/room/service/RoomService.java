package com.room.service;

import com.room.entity.RoomEntity;

import java.util.List;

public interface RoomService {
    List<String> getRoomList();

    RoomEntity getRoomInfo(String roomName);

}
