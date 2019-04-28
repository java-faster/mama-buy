package com.room.entity;

import java.util.Date;

public class BookRoomPojo {
    private String roomName;//会议室名称

    private Integer userId;//用户id

    private String bookDate;//预订日期

    private String timeSpan;//预订时间片段

    public String getRoomName() {
        return roomName;
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }
}
