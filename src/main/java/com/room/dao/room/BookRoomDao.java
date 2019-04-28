package com.room.dao.room;

import com.room.dao.BaseDao;
import com.room.entity.BookRoomEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookRoomDao extends BaseDao<BookRoomEntity> {
    //查询某一日期某一时间片段的某一会议室的预订信息
    BookRoomEntity queryRecordByClause(BookRoomEntity bookRoomEntity);

}
