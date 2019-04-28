package com.room.controller;

import com.room.common.DateUtils;
import com.room.common.R;
import com.room.dao.ledger.UserDao;
import com.room.entity.BookRoomEntity;
import com.room.entity.BookRoomPojo;
import com.room.entity.Room;
import com.room.service.BookRoomService;
import com.room.service.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.bouncycastle.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(value = "BookRoomController", description = "会议室预定")
@RestController
@RequestMapping("/book/room")
public class BookRoomController {

    @Autowired
    private BookRoomService bookRoomService;
    @Autowired
    private UserDao userDao;

//    private static final Logger logger = Logger.getLogger(BookRoomController.class);

    /**
     * 用户预订会议室
     */
    @RequestMapping(value = "/save", consumes="application/json")
    public R bookRoom(@RequestBody BookRoomPojo bookRoomPojo) throws ParseException {
//        logger.info("预定会议室");
        if (bookRoomPojo.getUserId()==null)
            return R.error("参数错误");
        int isExitUser = userDao.checkUserById(bookRoomPojo.getUserId());
        if(isExitUser <= 0) {
            return R.error(301, "无权操作");
        }
        BookRoomEntity bookRoomEntity = new BookRoomEntity();
        bookRoomEntity.setRoomName(bookRoomPojo.getRoomName());
        bookRoomEntity.setTimeSpan(bookRoomPojo.getTimeSpan());
        bookRoomEntity.setUserId(bookRoomPojo.getUserId());
        bookRoomEntity.setBookDate(new SimpleDateFormat("yyyy-MM-dd").parse(bookRoomPojo.getBookDate()));

        //判断是否过期
        if (!overDue(bookRoomEntity.getTimeSpan(),bookRoomEntity.getBookDate()))
            return R.error("已过期不能预定");
        return bookRoomService.bookRoom(bookRoomEntity);
    }

    /**
     * 获取会议室预订列表信息
     */
    @ApiOperation(value="获取用户列表", notes = "获取用户列表")
    @RequestMapping(value = "/list")
    public R getRoomList(String week) throws ParseException {
//        logger.info("获取会议室预订列表信息");
        if (!"0".equals(week) && !"1".equals(week))
            return R.error("参数错误");
        R r =bookRoomService.getBookRoomList(week);
//        List<String> roomList = roomService.getRoomList();
        return r;
    }


    /**
     * 取消预订  roomName bookDate timeSpan userId
     */
    @ApiOperation(value = "取消预定", notes = "取消预定")
    @RequestMapping(value = "/cancel" , consumes="application/json")
    public R cancelBookRoom(@RequestBody BookRoomPojo bookRoomPojo) throws ParseException {
//        logger.info("取消预订");
        if (bookRoomPojo.getUserId()==null)
            return R.error("参数错误");
        int isExitUser = userDao.checkUserById(bookRoomPojo.getUserId());
        if(isExitUser <= 0) {
            return R.error(301, "无权操作");
        }
        BookRoomEntity bookRoomEntity = new BookRoomEntity();
        bookRoomEntity.setRoomName(bookRoomPojo.getRoomName());
        bookRoomEntity.setTimeSpan(bookRoomPojo.getTimeSpan());
        bookRoomEntity.setUserId(bookRoomPojo.getUserId());
        bookRoomEntity.setBookDate(new SimpleDateFormat("yyyy-MM-dd").parse(bookRoomPojo.getBookDate()));

        //判断时间是否过期
        if (!overDue(bookRoomEntity.getTimeSpan(),bookRoomEntity.getBookDate()))
            return R.error("已过期不能取消");
        boolean flag = false;
        //userName deleted userId
        BookRoomEntity bookRoomInfo = bookRoomService.getBookRoomByClause(bookRoomEntity);
        if (bookRoomInfo==null)
            return R.error("无人预订");
        //判断用户是否是管理员
        if (!admin(bookRoomEntity.getUserId())){
            //判断是否是该用户的预订
            if(bookRoomInfo.getDeleted()==0
                    && bookRoomInfo.getUserId().equals(bookRoomEntity.getUserId())){
                flag = !flag;
            }else
                return R.error("不是您的预订");
        }else {
            flag = !flag;
        }
        if (flag){
            //取消订单
            bookRoomEntity.setUserName(bookRoomInfo.getUserName());
            bookRoomEntity.setDeleted(1);
            bookRoomEntity.setOperateTime(System.currentTimeMillis());
            bookRoomEntity.setUserId(bookRoomInfo.getUserId());
            bookRoomService.updateBookRoomInfo(bookRoomEntity);
        }
        return R.ok();
    }

    private boolean admin(Integer userId) {
        return userDao.selectRoleByUserId(userId) == 0;
    }

    private boolean overDue(String timeSpan, Date bookDate) throws ParseException {
        DateUtils dateUtils = new DateUtils();
        //预订日期和当前时间比较
        int result = dateUtils.compareDate(bookDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = "";
        if (result!=-1){
            if ("AM".equals(timeSpan))
                time = sdf.format(bookDate)+" 12:21:00";
            else if ("PM1".equals(timeSpan))
                time = sdf.format(bookDate)+" 15:31:00";
            else if ("PM2".equals(timeSpan))
                time = sdf.format(bookDate)+" 18:42:00";

            if (dateUtils.compareDate(time)==-1)
                return true;
            else
                return false;
        }else
            return false;
    }
}
