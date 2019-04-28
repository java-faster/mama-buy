package com.room.entity;

import java.io.Serializable;

/**
 * 会议室实体类
 */
public class RoomEntity implements Serializable {

    private Long roomId;

    private String roomName;//会议室名称

    private Integer state;//是否可预订  0:不可预订   1：可预订

    private Integer floor;//所在楼层

    private Integer seat;//会议室的座位数

    private Integer projector;//有无投影仪  0 有  1 无

    private Integer meetingDevice;//有无会议通  0 有  1 无

    private Integer window;//有无窗户  0 有  1 无

    private String position;//位置

    private String introduction;//简介

    private String createDate;//创建时间

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public Integer getState() {
        return state;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Integer getProjector() {
        return projector;
    }

    public void setProjector(Integer projector) {
        this.projector = projector;
    }

    public Integer getMeetingDevice() {
        return meetingDevice;
    }

    public void setMeetingDevice(Integer meetingDevice) {
        this.meetingDevice = meetingDevice;
    }

    public Integer getWindow() {
        return window;
    }

    public void setWindow(Integer window) {
        this.window = window;
    }
}
