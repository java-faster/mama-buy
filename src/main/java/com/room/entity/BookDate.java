package com.room.entity;

import java.util.Date;
import java.util.List;

public class BookDate {
//    private Date bookDate;

    private List<TimeSpan> timeSpan;

//    public Date getBookDate() {
//        return bookDate;
//    }

    public List<TimeSpan> getTimeSpan() {
        return timeSpan;
    }

//    public void setBookDate(Date bookDate) {
//        this.bookDate = bookDate;
//    }

    public void setTimeSpan(List<TimeSpan> timeSpan) {
        this.timeSpan = timeSpan;
    }
}
