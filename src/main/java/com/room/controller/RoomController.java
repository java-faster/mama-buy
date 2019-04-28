package com.room.controller;

import com.room.common.R;
import com.room.entity.RoomEntity;
import com.room.service.CreateRoomService;
import com.room.service.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "RoomController", description = "会议室信息")
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private CreateRoomService createRoomService;

//    private static final Logger logger = Logger.getLogger(RoomController.class);

    /**
     * 获取会议室详细信息
     */
    @ApiOperation(value = "获取会议室详细信息", notes = "获取会议室详细信息")
    @RequestMapping(value = "/getRoomInfo")
    public R roomInfo(String roomName) {
//        logger.info("获取会议室详细信息");
        if (roomName==null || "".equals(roomName.trim()))
            return R.error("参数错误");
        return R.ok().put("roomInfo",roomService.getRoomInfo(roomName));
    }

    @ApiOperation(value = "创建会议室", notes = "创建会议室")
    @RequestMapping(value = "/createroom")
    public R createRoom(@RequestBody RoomEntity roomEntity){
        String roomName=roomEntity.getRoomName();
        if(roomName==null){
            return R.error("请输入会议室名称！");
        }
        R r= createRoomService.createRoom(roomEntity);
        return r;
    }
}
