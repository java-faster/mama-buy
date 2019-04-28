package com.room.service.impl;

import com.room.common.DateUtils;
import com.room.common.R;
import com.room.dao.room.BookRoomDao;
import com.room.dao.room.RoomDao;
import com.room.entity.*;
import com.room.service.BookRoomService;
import com.room.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("bookRoomService")
public class BookRoomServiceImpl implements BookRoomService {

   @Autowired
    private BookRoomDao bookRoomDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private UserService userService;

    private DateUtils dateUtils = new DateUtils();

//    private static final Logger logger = Logger.getLogger(BookRoomService.class);

    /**
     * 预定会议室
     */
    @Override
    public R bookRoom( BookRoomEntity bookRoomEntity ) throws ParseException {
        //查询同日期同时间片段的同会议室是否已被预定
        bookRoomEntity.setDeleted(0);
        BookRoomEntity bookRoomInfo = getBookRoomByClause(bookRoomEntity);
        if (bookRoomInfo != null
                && bookRoomInfo.getUserId()!=bookRoomEntity.getUserId())
            return R.error("已被别人预订");

        if(bookRoomInfo == null) {
            String userName = userService.getUserNameByUserId(bookRoomEntity.getUserId().toString());

            bookRoomEntity.setBookId(0L);
            bookRoomEntity.setUserName(userName);
            saveBookRoomInfo(bookRoomEntity);
        }/*else {
            bookRoomEntity.setUserId(bookRoomInfo.getUserId());
            bookRoomEntity.setDeleted(0);
            bookRoomEntity.setOperateTime(System.currentTimeMillis());
            updateBookRoomInfo(bookRoomEntity);
        }*/
        return R.ok();
    }

    @Override
    public R getBookRoomList(String week) throws ParseException {
        Map<String,Object> map = new HashMap<>();
        String minDate = dateUtils.getToday();
        String maxDate = dateUtils.getDetailDate(minDate);
        if ("1".equals(week)){
            minDate = dateUtils.getDate(maxDate,1);
            maxDate = dateUtils.getDetailDate(minDate);
        }
        map.put("minDate",minDate);
        map.put("maxDate",maxDate);
        List<Room> roomList = initRoomList(minDate);
        List<BookRoomEntity> bookRoomList = bookRoomDao.queryList(map);
        roomList = getRoomList(roomList,bookRoomList,minDate);
        return R.ok().put("bookInfo",roomList).put("sdate",minDate);
    }

    private List<Room> getRoomList(List<Room> roomList, List<BookRoomEntity> bookRoomList,String minDate) throws ParseException {
        int size = bookRoomList.size();
        List<String> roomNameList = roomDao.queryList();
        for (int i=0;i<size;i++){

            BookRoomEntity bookRoomEntity = bookRoomList.get(i);
            String roomName = bookRoomEntity.getRoomName();
            int roomIndex = roomNameList.indexOf(roomName);
//            logger.info("roomIndex----"+roomIndex);

            Date bookDate = bookRoomEntity.getBookDate();
            int dateIndex = DateUtils.getNumber(bookDate,minDate);
//            logger.info("bookRoomEntity.getBookDate()"+bookRoomEntity.getBookDate());
//            logger.info("dateIndex"+dateIndex);

            String timeSpan = bookRoomEntity.getTimeSpan();
            int timeIndex = "AM".equals(timeSpan) ? 0: "PM1".equals(timeSpan) ? 1 : 2;
//            logger.info("timeIndex"+timeIndex);

            TimeSpan entity = roomList.get(roomIndex).getBookDate().get(dateIndex).getTimeSpan().get(timeIndex);
            entity.setUserName(bookRoomEntity.getUserName());
            entity.setStatus(1);
        }
        return roomList;
    }

    private List<Room> initRoomList(String minDate) throws ParseException {
        List<Room> rooms = new ArrayList<>();
        List<String> roomNameList = roomDao.queryList();
        int size = roomNameList.size();
        for (int i=0;i<size;i++){
            // 5 6 8 9 10 11
            Room room = new Room();
            room.setRoomName(roomNameList.get(i));
            room.setSdate(minDate.replace("-","/"));

            List<BookDate> bookDates = new ArrayList<>();
            for (int j=0;j<6;j++){
//                String date = DateUtils.getDetailDate(j,minDate);
                BookDate bookDate = new BookDate();
//                bookDate.setBookDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));

                List<TimeSpan> timeSpans = new ArrayList<>();
                for (int k = 0;k<3;k++){
                    TimeSpan timeSpan = new TimeSpan();
                    timeSpan.setTimeZone(k+1);
                    timeSpan.setStatus(0);
                    timeSpan.setUserName("");
                    timeSpans.add(timeSpan);
                }
                bookDate.setTimeSpan(timeSpans);
                bookDates.add(bookDate);
            }
            room.setBookDate(bookDates);
            rooms.add(room);
        }
        return rooms;
    }

    @Override
    public void saveBookRoomInfo(BookRoomEntity bookRoomEntity) {
        bookRoomDao.save(bookRoomEntity);
    }

    @Override
    public void updateBookRoomInfo(BookRoomEntity bookRoomEntity) {
        bookRoomDao.update(bookRoomEntity);
    }

    @Override
    public BookRoomEntity getBookRoomByClause(BookRoomEntity bookRoomEntity) {
         return bookRoomDao.queryRecordByClause(bookRoomEntity);
    }

}
