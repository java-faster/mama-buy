package com.room.entity;

import java.util.List;

public class Room {

    private String roomName;

    private List<BookDate> bookDate;

    private String sdate;

    public String getRoomName() {
        return roomName;
    }

    public List<BookDate> getBookDate() {
        return bookDate;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setBookDate(List<BookDate> bookDate) {
        this.bookDate = bookDate;
    }
}
