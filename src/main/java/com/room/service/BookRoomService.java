package com.room.service;

import com.room.common.R;
import com.room.entity.BookRoomEntity;
import com.room.entity.BookRoomPojo;
import com.room.entity.Room;

import java.text.ParseException;
import java.util.List;

public interface BookRoomService {

    //预定会议室
    R bookRoom( BookRoomEntity bookRoomEntity ) throws ParseException;

    //获取指定时间内会议室预订列表
    R getBookRoomList(String week) throws ParseException;

    void saveBookRoomInfo(BookRoomEntity bookRoomEntity);

    void updateBookRoomInfo(BookRoomEntity bookRoomEntity);

    BookRoomEntity getBookRoomByClause(BookRoomEntity bookRoomEntity);

//    R cancelBook(BookRoomEntity bookRoomEntity) throws ParseException;
}
