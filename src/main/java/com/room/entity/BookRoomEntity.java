package com.room.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议室预定实体类
 */
public class BookRoomEntity implements Serializable {

    private Long bookId;

    private String roomName;//会议室名称

    private String userName;//用户名

    private Integer userId;//用户id

    private Date bookDate;//预订日期

    private String timeSpan;//预订时间片段

    private Long operateTime;//更新时间 精确到毫秒

    private Integer deleted;//是否有效  0：有效  1：无效

    public Long getBookId() {
        return bookId;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public Long getOperateTime() {
        return operateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void setOperateTime(Long operateTime) {
        this.operateTime = operateTime;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
