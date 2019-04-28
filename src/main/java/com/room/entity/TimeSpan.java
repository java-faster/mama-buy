package com.room.entity;


public class TimeSpan{
    private int timeZone;

    private int status;

    private String userName;

    public int getTimeZone() {
        return timeZone;
    }

    public int getStatus() {
        return status;
    }

    public String getUserName() {
        return userName;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
